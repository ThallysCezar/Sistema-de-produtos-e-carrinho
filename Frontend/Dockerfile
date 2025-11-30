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
    echo '' >> /docker-entrypoint.sh && \
    echo '# Define BACKEND_URL padrão se não estiver definida' >> /docker-entrypoint.sh && \
    echo 'export BACKEND_URL="${BACKEND_URL:-http://backend:8080}"' >> /docker-entrypoint.sh && \
    echo '' >> /docker-entrypoint.sh && \
    echo '# Debug: mostra a URL do backend' >> /docker-entrypoint.sh && \
    echo 'echo "======================================"' >> /docker-entrypoint.sh && \
    echo 'echo "Configurando Nginx com BACKEND_URL:"' >> /docker-entrypoint.sh && \
    echo 'echo "$BACKEND_URL"' >> /docker-entrypoint.sh && \
    echo 'echo "======================================"' >> /docker-entrypoint.sh && \
    echo '' >> /docker-entrypoint.sh && \
    echo '# Substitui variáveis de ambiente no nginx.conf' >> /docker-entrypoint.sh && \
    echo 'envsubst '\''$BACKEND_URL'\'' < /etc/nginx/nginx.conf.template > /etc/nginx/nginx.conf' >> /docker-entrypoint.sh && \
    echo '' >> /docker-entrypoint.sh && \
    echo '# Debug: mostra a configuração gerada' >> /docker-entrypoint.sh && \
    echo 'echo "Configuração do proxy_pass gerada:"' >> /docker-entrypoint.sh && \
    echo 'grep -A 5 "location /api/" /etc/nginx/nginx.conf' >> /docker-entrypoint.sh && \
    echo 'echo "======================================"' >> /docker-entrypoint.sh && \
    echo '' >> /docker-entrypoint.sh && \
    echo '# Testa a configuração do Nginx' >> /docker-entrypoint.sh && \
    echo 'nginx -t' >> /docker-entrypoint.sh && \
    echo '' >> /docker-entrypoint.sh && \
    echo '# Inicia o Nginx' >> /docker-entrypoint.sh && \
    echo 'exec nginx -g "daemon off;"' >> /docker-entrypoint.sh && \
    chmod +x /docker-entrypoint.sh

EXPOSE 80

# Variável de ambiente padrão (será substituída no Render)
ENV BACKEND_URL=http://backend:8080

ENTRYPOINT ["/docker-entrypoint.sh"]
