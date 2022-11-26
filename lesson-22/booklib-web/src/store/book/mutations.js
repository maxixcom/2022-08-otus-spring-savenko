import Book from '@/model/Book'
import Author from '@/model/Author'
import Genre from '@/model/Genre'

export default {
  setBook: function (state, data) {
    state.book = Book.create(data)
  },
  setAuthors: function (state, data) {
    state.authors = data.map((value) => new Author(value))
  },
  setGenres: function (state, data) {
    state.genres = data.map((value) => new Genre(value))
  },
  setLoading: function (state, loading) {
    state.loading = loading
  }
}
