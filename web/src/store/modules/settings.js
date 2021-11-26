import defaultSettings from '@/settings'
import cookie from "js-cookie";

const { showSettings, fixedHeader, sidebarLogo } = defaultSettings

const themeKey = "THEME_COLOR";

function initThemeColor(){

  let themeColor = cookie.get(themeKey);
  themeColor=themeColor?JSON.parse(themeColor):{};
  for(let key  in themeColor){
    document.getElementsByTagName('body')[0].style.setProperty(key, themeColor[key]);
  }
  return themeColor;
}


const state = {
  showSettings: showSettings,
  fixedHeader: fixedHeader,
  sidebarLogo: sidebarLogo,
  themeColor:initThemeColor()
}


const mutations = {
  CHANGE_SETTING: (state, { key, value }) => {
    // eslint-disable-next-line no-prototype-builtins
    if (state.hasOwnProperty(key)) {
      state[key] = value
    }
  },
  UPDATE_THEME_COLOR:(state,themeColorObj) =>{
    for(let key  in themeColorObj){
      document.getElementsByTagName('body')[0].style.setProperty(key, themeColorObj[key]);
    }
    cookie.set(themeKey,JSON.stringify(themeColorObj));

    state.themeColor=themeColorObj;
  },
  CLEAR_THEME_COLOR:(state) =>{
    let themeColor = cookie.get(themeKey);
    themeColor=themeColor?JSON.parse(themeColor):{};
    for(let key  in themeColor){
      document.getElementsByTagName('body')[0].style.setProperty(key, '');
    }
    cookie.remove(themeKey);
    state.themeColor= {};
  },
}

const actions = {
  changeSetting({ commit }, data) {
    commit('CHANGE_SETTING', data)
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}

