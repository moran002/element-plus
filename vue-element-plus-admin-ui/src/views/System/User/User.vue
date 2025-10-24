<script setup lang="tsx">
import { ContentWrap } from '@/components/ContentWrap'
import { useI18n } from '@/hooks/web/useI18n'
import { Table } from '@/components/Table'
import { ElTag } from 'element-plus'
import { ref, unref, reactive, onMounted } from 'vue'
import { createUserApi, updateUserApi, deleteUserApi, getUsersApi } from '@/api/system/user'
import { useTable } from '@/hooks/web/useTable'
import { Search } from '@/components/Search'
import Write from './components/Write.vue'
import Detail from './components/Detail.vue'
import Password from './components/Password.vue'
import { Dialog } from '@/components/Dialog'
import { getRoleSimpleList } from '@/api/system/role'
import { CrudSchema, useCrudSchemas } from '@/hooks/web/useCrudSchemas'
import { BaseButton } from '@/components/Button'

const { t } = useI18n()

const { tableRegister, tableState, tableMethods } = useTable({
  fetchDataApi: async () => {
    const { currentPage, pageSize } = tableState
    const res = await getUsersApi({
      pageNo: unref(currentPage),
      pageSize: unref(pageSize),
      ...unref(searchParams)
    })
    return {
      list: res.data || [],
      total: res.total
    }
  },
  fetchDelApi: async () => {
    const res = await deleteUserApi(unref(ids))
    return !!res
  }
})
const { total, loading, dataList, pageSize, currentPage } = tableState
const { getList } = tableMethods

const crudSchemas = reactive<CrudSchema[]>([
  {
    field: 'selection',
    search: {
      hidden: true
    },
    form: {
      hidden: true
    },
    detail: {
      hidden: true
    },
    table: {
      type: 'selection'
    }
  },
  {
    field: 'index',
    label: t('userDemo.index'),
    form: {
      hidden: true
    },
    search: {
      hidden: true
    },
    detail: {
      hidden: true
    },
    table: {
      type: 'index'
    }
  },
  {
    field: 'nickName',
    label: t('userDemo.username')
  },
  {
    field: 'account',
    label: t('userDemo.account'),
    form: {
      component: 'Input'
    }
  },
  {
    field: 'mobile',
    label: t('userDemo.mobile')
  },
  {
    field: 'roleNames',
    label: t('userDemo.roleNames'),
    search: {
      hidden: true
    },
    form: {
      hidden: true
    }
  },
  {
    field: 'roleIds',
    label: t('userDemo.roleNames'),
    table: {
      hidden: true
    },
    detail: {
      hidden: true
    },
    search: {
      hidden: false,
      component: 'Select',
      value: [],
      componentProps: {
        multiple: false,
        collapseTags: true,
        maxCollapseTags: 1
      },
      optionApi: async () => {
        return roleData
      }
    },
    form: {
      component: 'Select',
      value: [],
      componentProps: {
        multiple: true,
        collapseTags: true,
        maxCollapseTags: 2
      },
      optionApi: async () => {
        return roleData
      }
    }
  },
  {
    field: 'email',
    label: t('userDemo.email'),
    form: {
      component: 'Input'
    },
    search: {
      hidden: true
    },
    table: {
      hidden: true
    }
  },
  {
    field: 'status',
    label: t('base.status'),
    search: {
      hidden: true
    },
    form: {
      component: 'Switch',
      value: true
    },
    detail: {
      slots: {
        default: (data: any) => {
          const status = data?.row?.status ?? data?.status
          return (
            <>
              <ElTag type={status ? 'danger' : 'success'}>
                {status ? t('userDemo.enable') : t('userDemo.disable')}
              </ElTag>
            </>
          )
        }
      }
    },
    slots: {
      default: (data: any) => {
        return (
          <>
            <ElTag type={data.row.status ? 'danger' : 'success'}>
              {data.row.status ? t('userDemo.enable') : t('userDemo.disable')}
            </ElTag>
          </>
        )
      }
    }
  },
  {
    field: 'remark',
    label: t('userDemo.remark'),
    table: {
      hidden: true
    },
    search: {
      hidden: true
    }
  },
  {
    field: 'createTime',
    label: t('userDemo.createTime'),
    form: {
      hidden: true
    },
    search: {
      hidden: true
    }
  },
  {
    field: 'action',
    label: t('userDemo.action'),
    form: {
      hidden: true
    },
    detail: {
      hidden: true
    },
    search: {
      hidden: true
    },
    table: {
      width: 400,
      slots: {
        default: (data: any) => {
          const row = data.row
          return (
            <>
              <BaseButton
                v-hasPermi="system:user:create"
                type="primary"
                onClick={() => action(row, 'edit')}
              >
                {t('exampleDemo.edit')}
              </BaseButton>
              <BaseButton
                v-hasPermi="system:user:query"
                type="success"
                onClick={() => action(row, 'detail')}
              >
                {t('exampleDemo.detail')}
              </BaseButton>
              <BaseButton
                v-hasPermi="system:user:password"
                type="warning"
                onClick={() => changePassword(row)}
              >
                修改密码
              </BaseButton>
              <BaseButton
                v-hasPermi="system:user:delete"
                type="danger"
                onClick={() => delData(row)}
              >
                {t('exampleDemo.del')}
              </BaseButton>
            </>
          )
        }
      }
    }
  }
])

const { allSchemas } = useCrudSchemas(crudSchemas)

const searchParams = ref({})
const setSearchParams = (params: any) => {
  currentPage.value = 1
  searchParams.value = params
  getList()
}

const dialogVisible = ref(false)
const dialogTitle = ref('')
const passwordDialogVisible = ref(false)
const currentUserId = ref('')

const currentRow = ref()
const actionType = ref('')

const AddAction = () => {
  dialogTitle.value = t('exampleDemo.add')
  currentRow.value = undefined
  dialogVisible.value = true
  actionType.value = ''
}

const delLoading = ref(false)
const ids = ref<string[]>([])

const delData = async (row: any) => {
  if (!row || !row.id) return

  delLoading.value = true

  try {
    await deleteUserApi(row.id)
    getList()
  } catch (error) {
    console.log(error)
  } finally {
    delLoading.value = false
  }
}

const action = (row: any, type: string) => {
  dialogTitle.value = t(type === 'edit' ? 'exampleDemo.edit' : 'exampleDemo.detail')
  actionType.value = type
  currentRow.value = { ...row }
  dialogVisible.value = true
}

const changePassword = (row: any) => {
  if (!row || !row.id) return
  currentUserId.value = row.id
  passwordDialogVisible.value = true
}

const writeRef = ref<InstanceType<typeof Write>>()
const passwordRef = ref<InstanceType<typeof Password>>()

const saveLoading = ref(false)

const save = async () => {
  const write = unref(writeRef)
  const formData = await write?.submit()
  if (formData) {
    saveLoading.value = true
    try {
      const res = formData.id ? await updateUserApi(formData) : await createUserApi(formData)
      if (res) {
        currentPage.value = 1
        getList()
      }
    } catch (error) {
      console.log(error)
    } finally {
      saveLoading.value = false
      dialogVisible.value = false
    }
  }
}

const passwordLoading = ref(false)
const savePassword = async () => {
  const passwordComponent = unref(passwordRef)
  passwordLoading.value = true
  try {
    await passwordComponent?.submit()
  } finally {
    passwordLoading.value = false
  }
}

const roleData = ref()
const initRoleData = async () => {
  const res = await getRoleSimpleList()
  if (res.data) {
    roleData.value = res.data
  }
}
onMounted(async () => {
  await initRoleData()
})
</script>

<template>
  <ContentWrap>
    <Search :schema="allSchemas.searchSchema" @reset="setSearchParams" @search="setSearchParams" />

    <div class="mb-10px">
      <BaseButton type="primary" @click="AddAction">{{ t('exampleDemo.add') }}</BaseButton>
    </div>
    <Table
      v-model:current-page="currentPage"
      v-model:page-size="pageSize"
      :columns="allSchemas.tableColumns"
      :data="dataList"
      :loading="loading"
      @register="tableRegister"
      :pagination="{
        total
      }"
    />

    <Dialog v-model="dialogVisible" :title="dialogTitle">
      <Write
        v-if="actionType !== 'detail'"
        ref="writeRef"
        :form-schema="allSchemas.formSchema"
        :current-row="currentRow"
        :action-type="actionType"
      />

      <Detail
        v-if="actionType === 'detail'"
        :detail-schema="allSchemas.detailSchema"
        :current-row="currentRow"
      />

      <template #footer>
        <BaseButton
          v-if="actionType !== 'detail'"
          type="primary"
          :loading="saveLoading"
          @click="save"
        >
          {{ t('exampleDemo.save') }}
        </BaseButton>
        <BaseButton @click="dialogVisible = false">{{ t('dialogDemo.close') }}</BaseButton>
      </template>
    </Dialog>

    <Dialog v-model="passwordDialogVisible" title="修改密码">
      <Password ref="passwordRef" :userId="currentUserId" @close="passwordDialogVisible = false" />
      <template #footer>
        <BaseButton type="primary" :loading="passwordLoading" @click="savePassword">
          确认修改
        </BaseButton>
        <BaseButton @click="passwordDialogVisible = false">取消</BaseButton>
      </template>
    </Dialog>
  </ContentWrap>
</template>
