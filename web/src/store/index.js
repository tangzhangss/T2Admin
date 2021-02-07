import Vuex from 'vuex'
import getters from './getters'
import app from './modules/app'
import settings from './modules/settings'
import permission from './modules/permission'

const store = Vuex.createStore({
  modules: {
    app,
    settings,
    permission
  },
  getters
})

export default store
