name: Spring Boot CI/CD Pipeline

on:
  push:
    branches:
      - main

  pull_request:
    branches:
      - main

env:
  IMAGE_NAME: ecommerce-backend

jobs:
  build-test-docker-deploy:
    runs-on: ubuntu-latest

    steps:

      # Checkout Repository
      - name: Checkout Source
        uses: actions/checkout@v4

      # Setup Java
      - name: Setup JDK 21
        uses: actions/setup-java@v4
        with:
          distribution: temurin
          java-version: '21'
          cache: maven

      # Build + Test
      - name: Build with Maven
        run: mvn clean verify

      # Upload JAR (Artifact)
      - name: Upload JAR
        uses: actions/upload-artifact@v4
        with:
          name: spring-boot-jar
          path: target/*.jar

      # Login DockerHub
      - name: Login to Docker Hub
        uses: docker/login-action@v3
        with:
          username: ${{ secrets.DOCKER_USERNAME }}
          password: ${{ secrets.DOCKER_PASSWORD }}

      # Build Docker Image
      - name: Build Docker Image
        run: |
          docker build \
          -t ${{ secrets.DOCKER_USERNAME }}/${{ env.IMAGE_NAME }}:${{ github.sha }} \
          -t ${{ secrets.DOCKER_USERNAME }}/${{ env.IMAGE_NAME }}:latest .

      # Push Docker Image
      - name: Push Docker Image
        run: |
          docker push ${{ secrets.DOCKER_USERNAME }}/${{ env.IMAGE_NAME }}:${{ github.sha }}
          docker push ${{ secrets.DOCKER_USERNAME }}/${{ env.IMAGE_NAME }}:latest

      # Configure Kubernetes
      - name: Configure kubectl
        uses: azure/setup-kubectl@v4

      - name: Configure kubeconfig
        run: |
          mkdir -p ~/.kube
          echo "${{ secrets.KUBE_CONFIG }}" | base64 -d > ~/.kube/config

      # Update Deployment
      - name: Deploy to Kubernetes
        run: |
          kubectl set image deployment/ecommerce-backend \
          ecommerce-backend=${{ secrets.DOCKER_USERNAME }}/${{ env.IMAGE_NAME }}:${{ github.sha }}

      # Wait for Rollout
      - name: Verify Deployment
        run: |
          kubectl rollout status deployment/ecommerce-backend --timeout=180s
