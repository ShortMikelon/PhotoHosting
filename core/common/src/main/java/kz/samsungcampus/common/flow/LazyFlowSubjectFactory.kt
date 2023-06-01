package kz.samsungcampus.common.flow

interface LazyFlowSubjectFactory {
    fun <T> create(valueLoader: ValueLoader<T>): LazyFlowSubject<T>
}