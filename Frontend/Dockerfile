FROM node:22-alpine AS build

WORKDIR /app

COPY package*.json ./
RUN npm ci --prefer-offline --no-audit

COPY . .

# Build para produção
ARG CONFIGURATION=production
RUN npm run build -- --configuration=${CONFIGURATION}

FROM nginx:alpine

# Instalar envsubst para substituir variáveis de ambiente
RUN apk add --no-cache gettext

# Copiar template do nginx
COPY nginx.conf /etc/nginx/nginx.conf.template

# Copiar aplicação buildada
COPY --from=build /app/dist/Frontend/browser /usr/share/nginx/html

# Script de inicialização para substituir variáveis de ambiente
RUN echo '#!/bin/sh' > /docker-entrypoint.sh && \
    echo 'set -e' >> /docker-entrypoint.sh && \
    echo 'envsubst < /etc/nginx/nginx.conf.template > /etc/nginx/nginx.conf' >> /docker-entrypoint.sh && \
    echo 'exec nginx -g "daemon off;"' >> /docker-entrypoint.sh && \
    chmod +x /docker-entrypoint.sh

EXPOSE 80

# Variável de ambiente padrão (será substituída no Render)
ENV BACKEND_URL=http://backend:8080

ENTRYPOINT ["/docker-entrypoint.sh"]
