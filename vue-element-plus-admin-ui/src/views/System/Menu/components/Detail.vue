<script setup lang="tsx">
import { PropType, ref } from 'vue'
import { Descriptions, DescriptionsSchema } from '@/components/Descriptions'
import { Icon } from '@/components/Icon'
import { ElTag } from 'element-plus'
import { useI18n } from '@/hooks/web/useI18n'

const { t } = useI18n()

defineProps({
  currentRow: {
    type: Object as PropType<any>,
    default: () => undefined
  }
})

const renderTag = (enable?: boolean) => {
  return (
    <ElTag type={!enable ? 'danger' : 'success'}>
      {enable ? t('common.enable') : t('common.disable')}
    </ElTag>
  )
}

const detailSchema = ref<DescriptionsSchema[]>([
  {
    field: 'type',
    label: t('menu.menuType'),
    span: 24,
    slots: {
      default: (data) => {
        const type = data.type
        return (
          <>{type === 0 ? t('menu.directory') : type === 1 ? t('menu.menu') : t('menu.button')}</>
        )
      }
    }
  },
  {
    field: 'parentName',
    label: t('menu.parentMenu')
  },
  {
    field: 'meta.title',
    label: t('menu.menuName')
  },
  {
    field: 'component',
    label: t('menu.component'),
    slots: {
      default: (data) => {
        const component = data.component
        return (
          <>
            {component === '#'
              ? t('menu.topDirectory')
              : component === '##'
                ? t('menu.subDirectory')
                : component}
          </>
        )
      }
    }
  },
  {
    field: 'name',
    label: t('menu.name')
  },
  {
    field: 'meta.icon',
    label: t('menu.icon'),
    slots: {
      default: (data) => {
        const icon = data.icon
        if (icon) {
          return (
            <>
              <Icon icon={icon} />
            </>
          )
        } else {
          return null
        }
      }
    }
  },
  {
    field: 'path',
    label: t('menu.path')
  },
  {
    field: 'meta.activeMenu',
    label: t('menu.activeMenu')
  },
  {
    field: 'permission',
    label: t('menu.permission')
  },
  {
    field: 'sort',
    label: t('menu.sort')
  },
  {
    field: 'meta.hidden',
    label: t('menu.hidden'),
    slots: {
      default: (data) => {
        return renderTag(data.meta?.hidden)
      }
    }
  },
  {
    field: 'meta.alwaysShow',
    label: t('menu.alwaysShow'),
    slots: {
      default: (data) => {
        return renderTag(data.meta?.alwaysShow)
      }
    }
  },
  {
    field: 'meta.noCache',
    label: t('menu.noCache'),
    slots: {
      default: (data) => {
        return renderTag(data.meta?.noCache)
      }
    }
  },
  {
    field: 'meta.breadcrumb',
    label: t('menu.breadcrumb'),
    slots: {
      default: (data) => {
        return renderTag(data.meta?.breadcrumb)
      }
    }
  },
  {
    field: 'meta.affix',
    label: t('menu.affix'),
    slots: {
      default: (data) => {
        return renderTag(data.meta?.affix)
      }
    }
  },
  {
    field: 'meta.noTagsView',
    label: t('menu.noTagsView'),
    slots: {
      default: (data) => {
        return renderTag(data.meta?.noTagsView)
      }
    }
  },
  {
    field: 'meta.canTo',
    label: t('menu.canTo'),
    slots: {
      default: (data) => {
        return renderTag(data.meta?.canTo)
      }
    }
  }
])
</script>

<template>
  <div class="detail-container max-h-500px overflow-auto">
    <Descriptions :schema="detailSchema" :data="currentRow || {}" />
  </div>
</template>

<style scoped>
.detail-container {
  height: 100%;
  min-height: 400px;
  max-height: 700px;
  overflow-y: auto;
}

.detail-container :deep(.el-descriptions) {
  height: 100%;
}

.detail-container :deep(.el-descriptions__body) {
  height: 100%;
  overflow-y: auto;
}
</style>
