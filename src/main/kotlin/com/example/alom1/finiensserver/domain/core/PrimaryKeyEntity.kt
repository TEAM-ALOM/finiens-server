package com.example.alom1.finiensserver.domain.core

import jakarta.persistence.*
import org.hibernate.proxy.HibernateProxy
import org.springframework.data.domain.Persistable
import java.io.Serializable
import java.util.*
import kotlin.jvm.Transient

abstract class PrimaryKeyEntity<T:Serializable>: Persistable<T> {
    open lateinit var id: T

    @Transient
    private var _isNew: Boolean = true

    @Suppress("INAPPLICABLE_JVM_NAME")
    @JvmName("apply")
    override fun getId(): T = id

    override fun isNew(): Boolean = _isNew

    private fun getIdentifier(obj: Any): Any {
        return if (obj is HibernateProxy) obj.hibernateLazyInitializer.identifier else (obj as PrimaryKeyEntity<T>).id
    }
    override fun equals(other: Any?): Boolean {
        if (other == null) {
            return false
        }

        if (other !is HibernateProxy && this::class != other::class) {
            return false
        }

        return id == getIdentifier(other)
    }

    override fun hashCode(): Int = Objects.hashCode(id)

    @PostPersist
    @PostLoad
    protected fun load() {
        _isNew = false
    }
}