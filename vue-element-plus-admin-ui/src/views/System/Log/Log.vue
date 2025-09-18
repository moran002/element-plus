<template>
  <ContentWrap>
    <!-- 搜索条件 -->
    <Search :schema="allSchemas.searchSchema" @search="setSearchParams" @reset="setSearchParams" />

    <!-- 表格 -->
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
  </ContentWrap>
</template>

<script setup lang="tsx">
import { ContentWrap } from '@/components/ContentWrap'
import { Search } from '@/components/Search'
import { Table } from '@/components/Table'
import { useTable } from '@/hooks/web/useTable'
import { FormSchema } from '@/components/Form'
import { TableColumn } from '@/components/Table'
import { getLogsApi } from '@/api/system/log'
import { ElTag } from 'element-plus'
import { ref, unref, reactive, onMounted } from 'vue'

// 查询条件schema
const searchSchema = ref<FormSchema[]>([
  {
    field: 'type',
    label: '类型',
    component: 'Select',
    componentProps: {
      options: [
        { label: '全部', value: '' },
        { label: '登入', value: 1 },
        { label: '登出', value: 2 }
      ]
    }
  },
  {
    field: 'nickName',
    label: '用户名称',
    component: 'Input',
    componentProps: {
      placeholder: '请输入用户名称'
    }
  }
])

// 表格列配置
const tableColumns = ref<TableColumn[]>([
  {
    field: 'id',
    label: 'ID',
    width: 80
  },
  {
    field: 'typeName',
    label: '类型',
    width: 100,
    slots: {
      default: (data: any) => {
        const type = data?.row?.type
        const typeName = data?.row?.typeName
        return <ElTag type={getTypeTagType(type)}>{typeName}</ElTag>
      }
    }
  },
  {
    field: 'nickName',
    label: '用户名称',
    minWidth: 80
  },
  {
    field: 'userAgent',
    label: '浏览器',
    minWidth: 100,
    showOverflowTooltip: true
  },
  {
    field: 'userIp',
    label: '用户IP',
    width: 100
  },
  {
    field: 'result',
    label: '登录结果',
    width: 100,
    slots: {
      default: (data: any) => {
        const result = data?.row?.result
        return <ElTag type="success">{result}</ElTag>
      }
    }
  },
  {
    field: 'createTime',
    label: '创建时间',
    width: 180
  }
])

const allSchemas = reactive({
  searchSchema,
  tableColumns
})

const { tableRegister, tableState, tableMethods } = useTable({
  fetchDataApi: async () => {
    const { currentPage, pageSize } = tableState
    const res = await getLogsApi({
      pageNo: unref(currentPage),
      pageSize: unref(pageSize),
      ...unref(searchParams)
    })
    return {
      list: res.data || [],
      total: res.total
    }
  }
})

const { total, loading, dataList, pageSize, currentPage } = tableState
const { getList } = tableMethods

const searchParams = ref({})
const setSearchParams = (params: any) => {
  currentPage.value = 1
  searchParams.value = params
  getList()
}

// 获取类型标签样式
const getTypeTagType = (type: number) => {
  const typeMap = {
    1: 'primary',
    2: 'warning',
    3: 'danger'
  }
  return typeMap[type] || 'info'
}

onMounted(() => {
  getList()
})
</script>
