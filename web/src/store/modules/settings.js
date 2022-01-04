import defaultSettings from '@/settings'
import cookie from "js-cookie";

const { showSettings, fixedHeader, sidebarLogo } = defaultSettings

const themeKey = "THEME_COLOR";
const themeLanguageKey = "THEME_LANGUAGE";

function initThemeColor(){

  let themeColor = cookie.get(themeKey);
  themeColor=themeColor?JSON.parse(themeColor):{};
  for(let key  in themeColor){
    document.getElementsByTagName('body')[0].style.setProperty(key, themeColor[key]);
  }
  return themeColor;
}
function initThemeLanguage(){
  let themeLanguage = cookie.get(themeLanguageKey);
  themeLanguage=themeLanguage||"zh-CN";
  return themeLanguage;
}

const state = {
  showSettings: showSettings,
  fixedHeader: fixedHeader,
  sidebarLogo: sidebarLogo,
  themeColor:initThemeColor(),
  themeLanguage:initThemeLanguage()
}


const mutations = {
  CHANGE_SETTING: (state, { key, value }) => {
    if (state.hasOwnProperty(key)) {
      state[key] = value
    }
  },
  UPDATE_THEME_LANGUAGE:(state,themeLanguage) =>{
    cookie.set(themeLanguageKey,themeLanguage);
    state.themeLanguage=themeLanguage;
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

