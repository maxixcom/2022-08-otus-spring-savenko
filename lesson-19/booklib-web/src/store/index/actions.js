export default {
  async fetchBooks({ commit }) {
    commit('setLoading', true)
    try {
      const books = await this.$axios.$get('/api/book')

      commit('setBooks', books)
    } catch (e) {
      console.log(e)
      commit('setBooks', [])
    } finally {
      commit('setLoading', false)
    }
  },

  async deleteBook({ commit, dispatch }, id) {
    try {
      await this.$axios.$delete('/api/book/' + id)
      await dispatch('fetchBooks')
    }
    catch (e) {
      console.log(e)
    }
  }

}
