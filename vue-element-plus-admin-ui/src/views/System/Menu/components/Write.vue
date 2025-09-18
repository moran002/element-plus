<script setup lang="tsx">
import { Form, FormSchema } from '@/components/Form'
import { useForm } from '@/hooks/web/useForm'
import { PropType, reactive, watch, ref, unref } from 'vue'
import { useValidator } from '@/hooks/web/useValidator'
import { useI18n } from '@/hooks/web/useI18n'
import { getSimpleListApi } from '@/api/system/menu'
import { cloneDeep } from 'lodash-es'

const { t } = useI18n()

const { required } = useValidator()

const props = defineProps({
  currentRow: {
    type: Object as PropType<any>,
    default: () => null
  }
})

const formSchema = reactive<FormSchema[]>([
  {
    field: 'type',
    label: t('menu.menuType'),
    component: 'RadioButton',
    value: 0,
    colProps: {
      span: 24
    },
    componentProps: {
      options: [
        {
          label: t('menu.directory'),
          value: 0
        },
        {
          label: t('menu.menu'),
          value: 1
        },
        {
          label: t('menu.button'),
          value: 2
        }
      ],
      on: {
        change: async (val: number) => {
          const formData = await getFormData()
          if (val === 2) {
            setSchema([
              {
                field: 'permission',
                path: 'hidden',
                value: false
              },
              {
                field: 'component',
                path: 'hidden',
                value: true
              },
              {
                field: 'name',
                path: 'hidden',
                value: true
              },
              {
                field: 'meta.icon',
                path: 'hidden',
                value: true
              },
              {
                field: 'path',
                path: 'hidden',
                value: true
              },
              {
                field: 'meta.activeMenu',
                path: 'hidden',
                value: true
              }
            ])
          } else {
            setSchema([
              {
                field: 'permission',
                path: 'hidden',
                value: true
              },
              {
                field: 'component',
                path: 'hidden',
                value: false
              },
              {
                field: 'name',
                path: 'hidden',
                value: false
              },
              {
                field: 'meta.icon',
                path: 'hidden',
                value: false
              },
              {
                field: 'path',
                path: 'hidden',
                value: false
              },
              {
                field: 'meta.activeMenu',
                path: 'hidden',
                value: false
              }
            ])
          }
          if (val === 1) {
            setSchema([
              {
                field: 'component',
                path: 'componentProps.disabled',
                value: false
              }
            ])
            setValues({
              component: unref(cacheComponent)
            })
          } else if (val === 0) {
            setSchema([
              {
                field: 'component',
                path: 'componentProps.disabled',
                value: true
              }
            ])

            if (formData.parentId === void 0) {
              setValues({
                component: '#'
              })
            } else {
              setValues({
                component: '##'
              })
            }
          }
        }
      }
    }
  },
  {
    field: 'parentId',
    label: t('menu.parentMenu'),
    component: 'TreeSelect',
    componentProps: {
      nodeKey: 'id',
      props: {
        label: (data: any) => t(data.label),
        value: 'value',
        children: 'children'
      },
      highlightCurrent: true,
      expandOnClickNode: false,
      checkStrictly: true,
      checkOnClickNode: true,
      clearable: true,
      on: {
        change: async (val: number) => {
          const formData = await getFormData()
          if (val && formData.type === 0) {
            setValues({
              component: '##'
            })
          } else if (!val && formData.type === 0) {
            setValues({
              component: '#'
            })
          } else if (formData.type === 1) {
            setValues({
              component: unref(cacheComponent) ?? ''
            })
          }
        }
      }
    },
    optionApi: async () => {
      const res = await getSimpleListApi()
      return res.data || []
    }
  },
  {
    field: 'meta.title',
    label: t('menu.menuName'),
    component: 'Input'
  },
  {
    field: 'component',
    label: t('menu.component'),
    component: 'Input',
    value: '#',
    componentProps: {
      disabled: true,
      placeholder: t('menu.componentPlaceholder'),
      on: {
        change: (val: string) => {
          cacheComponent.value = val
        }
      }
    }
  },
  {
    field: 'name',
    label: t('menu.name'),
    component: 'Input'
  },
  {
    field: 'meta.icon',
    label: t('menu.icon'),
    component: 'Input'
  },
  {
    field: 'path',
    label: t('menu.path'),
    component: 'Input'
  },
  {
    field: 'meta.activeMenu',
    label: t('menu.activeMenu'),
    component: 'Input'
  },
  {
    field: 'permission',
    label: t('menu.permission'),
    component: 'Input',
    hidden: true
  },
  {
    field: 'sort',
    label: t('menu.sort'),
    component: 'Input'
  },
  {
    field: 'meta.hidden',
    label: t('menu.hidden'),
    component: 'Switch'
  },
  {
    field: 'meta.alwaysShow',
    label: t('menu.alwaysShow'),
    component: 'Switch'
  },
  {
    field: 'meta.noCache',
    label: t('menu.noCache'),
    component: 'Switch'
  },
  {
    field: 'meta.breadcrumb',
    label: t('menu.breadcrumb'),
    component: 'Switch'
  },
  {
    field: 'meta.affix',
    label: t('menu.affix'),
    component: 'Switch'
  },
  {
    field: 'meta.noTagsView',
    label: t('menu.noTagsView'),
    component: 'Switch'
  },
  {
    field: 'meta.canTo',
    label: t('menu.canTo'),
    component: 'Switch'
  }
])

const rules = reactive({
  component: [required()],
  path: [required()],
  'meta.title': [required()]
})

const { formRegister, formMethods } = useForm()
const { setValues, getFormData, getElFormExpose, setSchema } = formMethods

const submit = async () => {
  const elForm = await getElFormExpose()
  const valid = await elForm?.validate().catch((err) => {
    console.log(JSON.stringify(err))
  })
  if (valid) {
    const formData = await getFormData()
    return formData
  }
}

const cacheComponent = ref('')

watch(
  () => props.currentRow,
  (value) => {
    if (!value) return
    const currentRow = cloneDeep(value)
    cacheComponent.value = currentRow.type === 1 ? currentRow.component : ''

    // 根据菜单类型设置字段显示/隐藏
    if (currentRow.type === 2) {
      // 按钮类型：隐藏不必要的字段，显示权限字段
      setSchema([
        {
          field: 'permission',
          path: 'hidden',
          value: false
        },
        {
          field: 'component',
          path: 'hidden',
          value: true
        },
        {
          field: 'name',
          path: 'hidden',
          value: true
        },
        {
          field: 'meta.icon',
          path: 'hidden',
          value: true
        },
        {
          field: 'path',
          path: 'hidden',
          value: true
        },
        {
          field: 'meta.activeMenu',
          path: 'hidden',
          value: true
        }
      ])
    } else {
      // 目录或菜单类型：显示常规字段，隐藏权限字段
      setSchema([
        {
          field: 'permission',
          path: 'hidden',
          value: true
        },
        {
          field: 'component',
          path: 'hidden',
          value: false
        },
        {
          field: 'name',
          path: 'hidden',
          value: false
        },
        {
          field: 'meta.icon',
          path: 'hidden',
          value: false
        },
        {
          field: 'path',
          path: 'hidden',
          value: false
        },
        {
          field: 'meta.activeMenu',
          path: 'hidden',
          value: false
        }
      ])
    }

    // 设置组件禁用状态
    if (currentRow.parentId === 0) {
      setSchema([
        {
          field: 'component',
          path: 'componentProps.disabled',
          value: true
        }
      ])
    } else {
      setSchema([
        {
          field: 'component',
          path: 'componentProps.disabled',
          value: false
        }
      ])
    }

    if (currentRow.type === 1) {
      setSchema([
        {
          field: 'component',
          path: 'componentProps.disabled',
          value: false
        }
      ])
    } else {
      setSchema([
        {
          field: 'component',
          path: 'componentProps.disabled',
          value: true
        }
      ])
    }

    setValues(currentRow)
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
