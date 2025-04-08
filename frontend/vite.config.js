import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

export default defineConfig({
  plugins: [react()],
  server: {
    host: true, // 🔥 Esto permite que Vite escuche en todas las IPs
    port: 5173  // (opcional) puedes cambiar el puerto si querés
  }
})
