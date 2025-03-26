FROM nginx:latest

COPY ./dist/products-app/browser/ /usr/share/nginx/html

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]