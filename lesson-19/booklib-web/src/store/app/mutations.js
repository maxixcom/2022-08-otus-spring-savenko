export default {
  /**
   * Theme and Layout
   */
  setGlobalTheme: function (state, theme) {
    this.app.vuetify.framework.theme.dark = theme === 'dark'
    state.globalTheme = theme
  }
}
