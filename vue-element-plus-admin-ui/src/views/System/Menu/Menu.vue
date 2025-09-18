<script setup lang="tsx">
import { reactive, ref, unref } from 'vue'
import { getListApi, createApi, updateApi, deleteApi } from '@/api/system/menu'
import { useTable } from '@/hooks/web/useTable'
import { useI18n } from '@/hooks/web/useI18n'
import { Table, TableColumn } from '@/components/Table'
import { ElMessageBox, ElMessage } from 'element-plus'
import { Icon } from '@/components/Icon'

import { ContentWrap } from '@/components/ContentWrap'
import Write from './components/Write.vue'
import Detail from './components/Detail.vue'
import { Dialog } from '@/components/Dialog'
import { BaseButton } from '@/components/Button'

const { t } = useI18n()

const { tableRegister, tableState, tableMethods } = useTable({
  fetchDataApi: async () => {
    const res = await getListApi()
    return {
      list: res.data || []
    }
  }
})

const { dataList, loading } = tableState
const { getList } = tableMethods

const tableColumns = reactive<TableColumn[]>([
  {
    field: 'index',
    label: t('userDemo.index'),
    type: 'index'
  },
  {
    field: 'meta.title',
    label: t('menu.menuName'),
    slots: {
      default: (data: any) => {
        const title = data.row.meta.title
        return <>{t(title)}</>
      }
    }
  },
  {
    field: 'component',
    label: t('menu.component')
  },
  {
    field: 'sort',
    label: t('menu.sort')
  },
  {
    field: 'action',
    label: t('userDemo.action'),
    width: 300,
    slots: {
      default: (data: any) => {
        const row = data.row
        return (
          <>
            <BaseButton type="primary" onClick={() => action(row, 'edit')}>
              {t('exampleDemo.edit')}
            </BaseButton>
            {(row.type === 0 || row.type === 1) && (
              <BaseButton type="warning" onClick={() => addChild(row)}>
                {t('exampleDemo.add')}
              </BaseButton>
            )}
            <BaseButton type="success" onClick={() => action(row, 'detail')}>
              {t('exampleDemo.detail')}
            </BaseButton>
            <BaseButton type="danger" onClick={() => delAction(row)}>
              {t('exampleDemo.del')}
            </BaseButton>
          </>
        )
      }
    }
  }
])

const dialogVisible = ref(false)
const dialogTitle = ref('')

const currentRow = ref()
const actionType = ref('')

const writeRef = ref<ComponentRef<typeof Write>>()

const saveLoading = ref(false)

const action = (row: any, type: string) => {
  dialogTitle.value = t(type === 'edit' ? 'exampleDemo.edit' : 'exampleDemo.detail')
  actionType.value = type
  currentRow.value = row
  dialogVisible.value = true
}

const AddAction = () => {
  dialogTitle.value = t('exampleDemo.add')
  currentRow.value = undefined
  dialogVisible.value = true
}

const addChild = (row: any) => {
  dialogTitle.value = t('exampleDemo.add')
  // 设置当前行为父级菜单
  currentRow.value = {
    parentId: row.id,
    type: row.type === 0 ? 1 : 2 // 如果父级是目录(type=0)，子级就是菜单(type=1)；如果父级是菜单(type=1)，子级就是按钮(type=2)
  }
  dialogVisible.value = true
}

const delAction = async (row: any) => {
  // 确认删除对话框
  const confirm = await ElMessageBox.confirm(t('common.delMessage'), t('common.delWarning'), {
    confirmButtonText: t('common.delOk'),
    cancelButtonText: t('common.delCancel'),
    type: 'warning'
  }).catch(() => false)

  if (confirm) {
    try {
      await deleteApi(row.id)
      // 删除成功后刷新列表
      await getList()
      ElMessage.success(t('common.delSuccess'))
    } catch (error) {
      console.error('删除菜单失败:', error)
    }
  }
}

const save = async () => {
  const write = unref(writeRef)
  const formData = await write?.submit()
  if (formData) {
    saveLoading.value = true
    try {
      if (formData.id) {
        // 更新菜单
        await updateApi(formData)
      } else {
        // 创建菜单
        await createApi(formData)
      }
      // 保存成功后刷新列表
      await getList()
      dialogVisible.value = false
    } catch (error) {
      console.error('保存菜单失败:', error)
    } finally {
      saveLoading.value = false
    }
  }
}
</script>

<template>
  <ContentWrap>
    <div class="mb-10px">
      <BaseButton type="primary" @click="AddAction">{{ t('exampleDemo.add') }}</BaseButton>
    </div>
    <Table
      :columns="tableColumns"
      node-key="id"
      :data="dataList"
      :loading="loading"
      @register="tableRegister"
    />
  </ContentWrap>

  <Dialog v-model="dialogVisible" :title="dialogTitle">
    <Write v-if="actionType !== 'detail'" ref="writeRef" :current-row="currentRow" />

    <Detail v-if="actionType === 'detail'" :current-row="currentRow" />

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
</template>
