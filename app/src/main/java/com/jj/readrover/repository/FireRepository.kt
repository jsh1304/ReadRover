package com.jj.readrover.repository

import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.jj.readrover.data.DataOrException
import com.jj.readrover.model.MBook
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FireRepository @Inject constructor(
    private val queryBook: Query
    ){

    // DB에 저장된 모든 책 가져오기 메서드
    suspend fun getAllBooksFromDatabase(): DataOrException<List<MBook>, Boolean, Exception> {
        val dataOrException = DataOrException<List<MBook>, Boolean, Exception>()

        try {
            dataOrException.loading = true // 로딩 실행
            // 모든 책 가져와서 dataOrException의 data에 저장
            dataOrException.data = queryBook.get().await().documents.map { documentSnapshot ->
                documentSnapshot.toObject(MBook::class.java)!!
            }
            // data가 저장되었다면 로딩 종료
            if (!dataOrException.data.isNullOrEmpty()) dataOrException.loading = false
        }catch (exception: FirebaseFirestoreException){ // 예외
            dataOrException.e = exception
        }
        return dataOrException
    }
}