<script setup lang="ts">
import { Form, FormSchema } from '@/components/Form'
import { useForm } from '@/hooks/web/useForm'
import { PropType, reactive, watch } from 'vue'
import { useValidator } from '@/hooks/web/useValidator'

const { required } = useValidator()

const props = defineProps({
  currentRow: {
    type: Object as PropType<any>,
    default: () => undefined
  },
  formSchema: {
    type: Array as PropType<FormSchema[]>,
    default: () => []
  },
  actionType: {
    type: String as PropType<string>,
    default: ''
  }
})

const rules = reactive({
  nickName: [required()],
  account: [required()]
})

const { formRegister, formMethods } = useForm()
const { setValues, getFormData, getElFormExpose } = formMethods

const submit = async () => {
  const elForm = await getElFormExpose()
  const valid = await elForm?.validate().catch((err) => {
    console.log(err)
  })
  if (valid) {
    const formData = await getFormData()
    return formData
  }
}

// 动态设置account字段的disabled属性
const setAccountFieldDisabled = () => {
  const accountField = props.formSchema.find((item) => item.field === 'account')
  if (accountField) {
    // 如果是编辑模式且当前行有数据，则禁用account字段
    const isEditMode = props.actionType === 'edit' && props.currentRow
    accountField.componentProps = {
      ...accountField.componentProps,
      disabled: isEditMode
    }
  }
}

// 监听actionType和currentRow变化，动态设置account字段的disabled状态
watch(
  () => [props.actionType, props.currentRow],
  () => {
    setAccountFieldDisabled()
  },
  {
    immediate: true,
    deep: true
  }
)

watch(
  () => props.currentRow,
  (currentRow) => {
    if (!currentRow) return
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
