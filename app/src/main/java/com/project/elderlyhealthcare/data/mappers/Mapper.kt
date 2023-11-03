package com.project.elderlyhealthcare.data.mappers

interface Mapper<E, M> {
    fun fromEntity(from: E): M
    fun toEntity(from: M): E
}


