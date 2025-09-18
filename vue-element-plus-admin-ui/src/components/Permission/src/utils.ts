import { useI18n } from '@/hooks/web/useI18n'
import { useUserStore } from '@/store/modules/user'

export const hasPermi = (value: string) => {
  const userStore = useUserStore()
  const { t } = useI18n()
  const permission = userStore.getUserInfo?.permissions || []
  if (!value) {
    throw new Error(t('permission.hasPermission'))
  }
  if (permission.includes(value)) {
    return true
  }
  return false
}
