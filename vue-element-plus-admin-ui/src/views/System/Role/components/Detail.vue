<script setup lang="tsx">
import { PropType, ref, unref, nextTick, watch } from 'vue'
import { Descriptions, DescriptionsSchema } from '@/components/Descriptions'
import { ElTag, ElTree } from 'element-plus'
import { findIndex } from '@/utils'
import { useI18n } from '@/hooks/web/useI18n'

const { t } = useI18n()

const props = defineProps({
  currentRow: {
    type: Object as PropType<any>,
    default: () => undefined
  },
  menuData: {
    type: Array as PropType<any[]>,
    default: () => []
  }
})

const filterPermissionName = (value: string) => {
  const index = findIndex(unref(currentTreeData)?.permissionList || [], (item) => {
    return item.value === value
  })
  return (unref(currentTreeData)?.permissionList || [])[index].label ?? ''
}

const renderTag = (enable?: boolean) => {
  return <ElTag type={!enable ? 'danger' : 'success'}>{enable ? '启用' : '禁用'}</ElTag>
}

const treeRef = ref<typeof ElTree>()

const currentTreeData = ref()
const nodeClick = (treeData: any) => {
  currentTreeData.value = treeData
}

const treeData = ref<any[]>([])

// 转换API返回的数据结构：value -> id，并处理国际化
const transformData = (data: any[]) => {
  return data.map((item) => ({
    id: item.value,
    label: t(item.label),
    meta: item.meta || {},
    children: item.children ? transformData(item.children) : undefined
  }))
}

// 根据角色分配的菜单ID过滤菜单数据
const filterAssignedMenus = (data: any[], menuIds: number[]) => {
  return data.filter((item) => {
    if (menuIds.includes(item.id)) {
      return true
    }
    if (item.children && item.children.length > 0) {
      item.children = filterAssignedMenus(item.children, menuIds)
      return item.children.length > 0
    }
    return false
  })
}

// 监听menuData和currentRow变化，过滤出角色分配的菜单
watch(
  [() => props.menuData, () => props.currentRow?.menuIds],
  ([newMenuData, menuIds]) => {
    if (newMenuData && newMenuData.length > 0 && menuIds) {
      const transformedData = transformData(newMenuData)
      treeData.value = filterAssignedMenus(transformedData, menuIds)
    } else if (newMenuData && newMenuData.length > 0) {
      treeData.value = transformData(newMenuData)
    }
  },
  { immediate: true }
)

const detailSchema = ref<DescriptionsSchema[]>([
  {
    field: 'name',
    label: '角色名称'
  },
  {
    field: 'remark',
    label: '备注',
    span: 24
  },
  {
    field: 'menuIds',
    label: '菜单分配',
    span: 24,
    slots: {
      default: () => {
        return (
          <>
            <div class="flex w-full">
              <div class="flex-1">
                <ElTree
                  ref={treeRef}
                  node-key="id"
                  props={{ children: 'children', label: 'label' }}
                  highlight-current
                  expand-on-click-node={false}
                  data={treeData.value}
                  onNode-click={nodeClick}
                >
                  {{
                    default: ({ data }: { data: any }) => {
                      return <span>{data.label}</span>
                    }
                  }}
                </ElTree>
              </div>
              <div class="flex-1">
                {unref(currentTreeData)
                  ? unref(currentTreeData)?.meta?.permission?.map((v: string) => {
                      return <ElTag class="ml-2 mt-2">{filterPermissionName(v)}</ElTag>
                    })
                  : null}
              </div>
            </div>
          </>
        )
      }
    }
  }
])
</script>

<template>
  <Descriptions :schema="detailSchema" :data="currentRow || {}" />
</template>
