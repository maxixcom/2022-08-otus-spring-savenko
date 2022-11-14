import configs from '../../configs'
import mutations from './mutations'

const { theme  } = configs
const { globalTheme } = theme

// state initial values
const state = () => ({
  // app theme dark or light
  globalTheme
})

export default {
  namespaced: true,
  state,
  mutations,
}
