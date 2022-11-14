import theme from '@/configs/theme.js'

export default {
  theme: {
    dark: theme.globalTheme === 'dark',
    options: {
      customProperties: true
    },
    themes: {
      dark: theme.dark,
      light: theme.light
    }
  }
}
