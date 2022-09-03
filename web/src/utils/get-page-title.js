import defaultSettings from '@/settings'

const title = defaultSettings.title || 'T2Admin'

export default function getPageTitle(pageTitle) {
  if (pageTitle) {
    return `${pageTitle} - ${title}`
  }
  return `${title}`
}
