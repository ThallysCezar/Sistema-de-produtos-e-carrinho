// Ambiente de PRODUÇÃO (Render)
// No Render, o Nginx vai fazer proxy de /api para o backend
export const environment = {
  production: true,
  apiUrl: '/api', // Nginx faz proxy para o backend interno do Render
  appName: 'Desafio2',
  enableDebug: false
};
