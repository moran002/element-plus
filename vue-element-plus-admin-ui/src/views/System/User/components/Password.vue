<script setup lang="ts">
import { Form } from '@/components/Form'
import { useForm } from '@/hooks/web/useForm'
import { reactive, ref } from 'vue'
import { useValidator } from '@/hooks/web/useValidator'
import { updatePasswordApi } from '@/api/system/user'
import { ElMessage } from 'element-plus'

const { required } = useValidator()

const props = defineProps({
  userId: {
    type: String,
    required: true
  }
})

const emit = defineEmits(['close'])

// 优化表单布局，使密码字段上下排列
const formSchema = reactive([
  {
    field: 'password',
    label: '新密码',
    component: 'InputPassword',
    value: '',
    colProps: {
      span: 24 // 使用全宽
    },
    componentProps: {
      style: {
        width: '100%'
      },
      showPassword: true,
      placeholder: '请输入新密码'
    }
  },
  {
    field: 'confirmPassword',
    label: '确认密码',
    component: 'InputPassword',
    value: '',
    colProps: {
      span: 24 // 使用全宽
    },
    componentProps: {
      style: {
        width: '100%'
      },
      showPassword: true,
      placeholder: '请再次输入新密码'
    }
  }
])

const { formRegister, formMethods } = useForm()
const { getFormData, getElFormExpose } = formMethods

// 用于存储表单数据
const formData = ref<any>({
  password: '',
  confirmPassword: ''
})

// 监听表单数据变化
const updateFormData = async () => {
  formData.value = await getFormData()
}

const rules = reactive({
  password: [
    required(),
    {
      validator: (rule: any, value: string, callback: Function) => {
        updateFormData()
        if (formData.value.confirmPassword && value !== formData.value.confirmPassword) {
          callback(new Error('两次输入的密码不一致'))
        } else {
          callback()
        }
      },
      trigger: 'blur'
    }
  ],
  confirmPassword: [
    required(),
    {
      validator: (rule: any, value: string, callback: Function) => {
        updateFormData()
        if (value !== formData.value.password) {
          callback(new Error('两次输入的密码不一致'))
        } else {
          callback()
        }
      },
      trigger: 'blur'
    }
  ]
})
const loading = ref(false)

const submit = async () => {
  const elForm = await getElFormExpose()
  const valid = await elForm?.validate().catch((err) => {
    console.log(err)
  })

  if (valid) {
    loading.value = true
    try {
      const data = await getFormData()
      await updatePasswordApi({
        id: props.userId,
        password: data.password
      })
      ElMessage.success('密码修改成功')
      emit('close')
    } catch (error) {
      console.error(error)
      ElMessage.error('密码修改失败')
    } finally {
      loading.value = false
    }
  }
}

defineExpose({
  submit,
  loading
})
</script>

<template>
  <Form :rules="rules" @register="formRegister" :schema="formSchema" />
</template>
