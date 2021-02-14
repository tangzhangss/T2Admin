import SvgIcon from '@/components/SvgIcon'// svg component
import {myApp} from "../../config/global";
// register globally
myApp.component('svg-icon', SvgIcon)

const req = require.context('./svg', false, /\.svg$/)
const requireAll = requireContext => requireContext.keys().map(requireContext)
requireAll(req)
