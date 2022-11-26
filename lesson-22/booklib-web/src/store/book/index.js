import mutations from './mutations'
import actions from './actions'
import Book from '@/model/Book'

const state = () => ({
  book: Book.empty(),
  authors: [],
  genres: [],
  loading: true
})

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
