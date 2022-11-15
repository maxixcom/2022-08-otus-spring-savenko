import mutations from './mutations'
import actions from './actions'

const state = () => ({
  books: [],
  loading: true
})

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
