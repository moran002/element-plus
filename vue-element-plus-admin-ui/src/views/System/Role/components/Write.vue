<script setup lang="tsx">
import { Form, FormSchema } from '@/components/Form'
import { useForm } from '@/hooks/web/useForm'
import { PropType, reactive, watch, ref, unref, nextTick, onMounted } from 'vue'
import { useValidator } from '@/hooks/web/useValidator'
import { useI18n } from '@/hooks/web/useI18n'
import { ElTree } from 'element-plus'

import { filter } from '@/utils/tree'

const { t } = useI18n()
const { required } = useValidator()

const props = defineProps({
  currentRow: {
    type: Object as PropType<any>,
    default: () => null
  },
  menuData: {
    type: Array as PropType<any[]>,
    default: () => []
  }
})

const treeRef = ref<typeof ElTree>()

const formSchema = ref<FormSchema[]>([
  {
    field: 'name',
    label: t('role.roleName'),
    component: 'Input',
    colProps: {
      span: 24
    },
    componentProps: {
      placeholder: t('role.roleNamePlaceholder')
    }
  },
  {
    field: 'remark',
    label: t('userDemo.remark'),
    component: 'Input',
    colProps: {
      span: 24
    },
    componentProps: {
      placeholder: t('role.remarkPlaceholder'),
      type: 'textarea',
      rows: 3
    }
  },
  {
    field: 'menuIds',
    label: t('role.menu'),
    colProps: {
      span: 24
    },
    formItemProps: {
      slots: {
        default: () => {
          return (
            <>
              <div class="flex w-full">
                <div class="flex-1">
                  <ElTree
                    ref={treeRef}
                    show-checkbox
                    node-key="id"
                    highlight-current
                    expand-on-click-node={false}
                    data={unref(treeData)}
                    key={unref(treeData).length} // 强制重新渲染
                    check-strictly={false}
                  >
                    {{
                      default: ({ data }: { data: any }) => {
                        return <span>{data.label}</span>
                      }
                    }}
                  </ElTree>
                </div>
              </div>
            </>
          )
        }
      }
    }
  }
])

const rules = reactive({
  name: [required()],
  remark: []
})

const { formRegister, formMethods } = useForm()
const { setValues, getFormData, getElFormExpose } = formMethods

const treeData = ref([])

// 转换API返回的数据结构：value -> id，并处理国际化
const transformData = (data: any[]) => {
  return data.map((item) => ({
    id: item.value,
    label: t(item.label),
    meta: item.meta || {},
    children: item.children ? transformData(item.children) : undefined
  }))
}

const getMenuList = () => {
  if (props.menuData && props.menuData.length > 0) {
    treeData.value = transformData(props.menuData)

    // 设置选中状态（编辑时）
    if (props.currentRow && props.currentRow.menuIds) {
      // 等待ElTree组件完全渲染
      nextTick(() => {
        nextTick(() => {
          // 设置选中的菜单ID（只设置实际选中的节点）
          unref(treeRef)?.setCheckedKeys(props.currentRow.menuIds, false)
        })
      })
    }
  }
}
// 组件挂载后处理菜单数据
onMounted(() => {
  getMenuList()
})

const submit = async () => {
  const elForm = await getElFormExpose()
  const valid = await elForm?.validate().catch(() => {
    return false
  })

  if (valid) {
    const formData = await getFormData()
    const checkedKeys = unref(treeRef)?.getCheckedKeys() || []

    // 移除不需要的参数
    delete formData.menu
    delete formData.createTime

    // 获取所有选中的菜单ID（包括父节点）
    // 需要手动处理以确保父节点ID被包含
    const allMenuIds = getAllParentMenuIds(checkedKeys, unref(treeData))
    formData.menuIds = allMenuIds
    return formData
  }
}

// 获取所有父菜单ID（包括所有上级菜单）
const getAllParentMenuIds = (checkedKeys: any[], menuData: any[]): any[] => {
  const result = [...checkedKeys]

  // 递归查找菜单树中的所有父节点
  const findParentIds = (nodes: any[], targetIds: any[]) => {
    const parentIds = new Set()

    nodes.forEach((node) => {
      if (node.children && node.children.length > 0) {
        // 检查子节点是否有被选中的
        const hasSelectedChild = node.children.some(
          (child) => targetIds.includes(child.id) || parentIds.has(child.id)
        )

        if (hasSelectedChild) {
          parentIds.add(node.id)
        }

        // 递归检查子节点
        const childParentIds = findParentIds(node.children, targetIds)
        childParentIds.forEach((id) => parentIds.add(id))
      }
    })

    return parentIds
  }

  // 多次查找确保所有层级都被处理
  let previousSize = 0
  let currentSize = result.length

  while (currentSize > previousSize) {
    const parentIds = findParentIds(menuData, result)
    parentIds.forEach((id) => {
      if (!result.includes(id)) {
        result.push(id)
      }
    })
    previousSize = currentSize
    currentSize = result.length
  }

  return [...new Set(result)]
}

watch(
  () => props.currentRow,
  (currentRow) => {
    if (!currentRow) return
    setValues(currentRow)

    // 重新设置选中状态（只设置实际选中的叶子节点，不设置父节点）
    if (currentRow.menuIds && treeData.value.length > 0) {
      nextTick(() => {
        // 只设置实际选中的菜单ID，不设置父节点（用于显示正确的选中状态）
        unref(treeRef)?.setCheckedKeys(currentRow.menuIds, false)
      })
    }
  },
  {
    deep: true,
    immediate: true
  }
)

defineExpose({
  submit
})
</script>

<template>
  <Form :rules="rules" @register="formRegister" :schema="formSchema" />
</template>
