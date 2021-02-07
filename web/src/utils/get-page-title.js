import defaultSettings from '@/settings'

const title = defaultSettings.title || 'TZCC-REN ADMIN'

export default function getPageTitle(pageTitle) {
  if (pageTitle) {
    return `${pageTitle} - ${title}`
  }
  return `${title}`
}
