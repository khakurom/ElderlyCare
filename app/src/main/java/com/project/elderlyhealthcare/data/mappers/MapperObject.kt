package com.project.elderlyhealthcare.data.mappers

interface MapperObject<E,M> {
    fun fromObjectEntity(from: E): M
    fun toObjectEntity(from: M): E
}