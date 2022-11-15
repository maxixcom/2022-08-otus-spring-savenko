export default {
  async loadNewBook({ commit, dispatch }) {
    commit('setLoading', true)
    try {

      await dispatch('fetchAuthors')
      await dispatch('fetchGenres')

    } catch (e) {
      console.log(e)
      commit('setBook', {})
      commit('setAuthors', [])
      commit('setGenres', [])
    } finally {
      commit('setLoading', false)
    }
  },

  async loadEditBook({ commit, dispatch }, id) {
    commit('setLoading', true)
    try {

      await dispatch('fetchAuthors')
      await dispatch('fetchGenres')
      await dispatch('fetchBook', id)

    } catch (e) {
      console.log(e)
      commit('setBook', {})
      commit('setAuthors', [])
      commit('setGenres', [])
    } finally {
      commit('setLoading', false)
    }
  },

  fetchBook({ commit }, id) {
    return this.$axios.$get('/api/book/' + id)
      .then((book) => commit('setBook', {
        id: book?.id,
        title: book?.title,
        authorId: book?.author?.id,
        genreId: book?.genre?.id
      }))
  },

  fetchAuthors({ commit }) {
    return this.$axios.$get('/api/author')
      .then((authors) => commit('setAuthors', authors))
  },

  fetchGenres({ commit }) {
    return this.$axios.$get('/api/genre')
      .then((genres) => commit('setGenres', genres))
  },

  createBook({ commit, dispatch }, { title, authorId, genreId }) {
    commit('setLoading', true)

    return this.$axios.$post('/api/book', { title, authorId, genreId })
      .finally(() => commit('setLoading', false))
  },

  editBook({ commit, dispatch }, { id, title, authorId, genreId }) {
    commit('setLoading', true)

    return this.$axios.$put('/api/book/' + id, { title, authorId, genreId })
      .finally(() => commit('setLoading', false))
  }
}
