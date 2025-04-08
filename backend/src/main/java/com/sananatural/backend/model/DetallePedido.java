package com.sananatural.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "detalle_pedido")
public class DetallePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El producto no puede ser nulo")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    @Column(nullable = false)
    private int cantidad;

    @JsonIgnore
    @NotNull(message = "La lista de pedido no puede ser nula")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lista_id", nullable = false)
    private ListaDePedido lista;

    // Constructores
    public DetallePedido() {
        // Constructor vacío necesario para JPA
    }

    public DetallePedido(Producto producto, int cantidad) {
        this.producto = Objects.requireNonNull(producto, "El producto no puede ser nulo");
        this.cantidad = cantidad;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public ListaDePedido getLista() {
        return lista;
    }

    // Setters con validación
    public void setProducto(Producto producto) {
        this.producto = Objects.requireNonNull(producto, "El producto no puede ser nulo");
    }

    public void setCantidad(int cantidad) {
        if (cantidad < 1) {
            throw new IllegalArgumentException("La cantidad debe ser al menos 1");
        }
        this.cantidad = cantidad;
    }

    public void setLista(ListaDePedido lista) {
        // Mantiene consistencia bidireccional
        if (this.lista != null) {
            this.lista.getDetalles().remove(this);
        }
        this.lista = Objects.requireNonNull(lista, "La lista no puede ser nula");
        if (!lista.getDetalles().contains(this)) {
            lista.getDetalles().add(this);
        }
    }

    // equals y hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DetallePedido that = (DetallePedido) o;
        return cantidad == that.cantidad &&
                Objects.equals(id, that.id) &&
                Objects.equals(producto, that.producto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, producto, cantidad);
    }

    // toString
    @Override
    public String toString() {
        return "DetallePedido{" +
                "id=" + id +
                ", producto=" + (producto != null ? producto.getId() : "null") +
                ", cantidad=" + cantidad +
                ", listaId=" + (lista != null ? lista.getId() : "null") +
                '}';
    }
}