package com.jj.readrover.screens.details

import android.util.Log
import android.widget.Space
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.jj.readrover.components.ReaderAppBar
import com.jj.readrover.components.RoundedButton
import com.jj.readrover.data.Resource
import com.jj.readrover.model.Item
import com.jj.readrover.model.MBook
import com.jj.readrover.navigation.ReaderScreens

@Composable
fun BookDetailsScreen(
    navController: NavController,
    bookId: String,
    viewModel: DetailsViewModel = hiltViewModel(),
) {
    
    Scaffold(topBar = {
        ReaderAppBar(title = "책 상세정보",
            icon = Icons.Default.ArrowBack,
            showProfile = false,
            navController = navController) {
            navController.navigate(ReaderScreens.SearchScreen.name)
        }
    }) {
        Surface(modifier = Modifier
            .padding(3.dp)
            .fillMaxSize()) {

            Column(modifier = Modifier.padding(top = 12.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally) {

                // produceState는 비동기 작업의 결과를 관리리
               val bookInfo = produceState<Resource<Item>>(initialValue = Resource.Loading()){
                    value = viewModel.getBookInfo(bookId) // 책 정보를 가져옴
                }.value

                if (bookInfo.data == null) { // 책 정보 없을 경우
                    Row() {
                        LinearProgressIndicator()
                        Text(text = "로딩 중")
                    }
                } else { // 책 정보 존재 할 경우
                    ShowBookDetails(bookInfo, navController)
                }
                Log.d("TAG", "BookDetailsScreen: ${bookInfo.data.toString()}")
            }
        }

    }
}

@Composable
fun ShowBookDetails(bookInfo: Resource<Item>, navController: NavController) {
    val bookData =  bookInfo.data?.volumeInfo
    val googleBookId = bookInfo.data?.id

    Card(modifier = Modifier.padding(34.dp),
        shape = CircleShape,
        elevation = 4.dp) {
        Image(painter = rememberImagePainter(data = bookData!!.imageLinks.thumbnail),
            contentDescription = "Book image",
            modifier = Modifier
                .width(90.dp)
                .height(90.dp)
                )
    }
    
    Text(text = bookData?.title.toString(),
        style = MaterialTheme.typography.h6,
        overflow = TextOverflow.Ellipsis,
        maxLines = 15)
    
    Text(text = "저자: ${bookData?.authors.toString()}")
    Text(text = "쪽수: ${bookData?.pageCount.toString()}")
    Text(text = "카테고리: ${bookData?.categories.toString()}",
        style = MaterialTheme.typography.subtitle1)
    Text(text = "발행일: ${bookData?.publishedDate.toString()}",
        style = MaterialTheme.typography.subtitle1)

    // 컴포넌트간 간격 조절
    Spacer(modifier = Modifier.height(5.dp))

    // HtmlCompat.fromHtml을 사용하여 HTML 형식의 설명을 일반 텍스트로 변환
    val cleanDescription = HtmlCompat.fromHtml(bookData!!.description,
        HtmlCompat.FROM_HTML_MODE_LEGACY).toString()

    // 현재 디스플레이의 해상도
    val localDims = LocalContext.current.resources.displayMetrics

    // 책소개 텍스트에 대한 Surface
    // Surface 높이를 디스플레이 해상도의 9%로 설정
    Surface(modifier = Modifier
        .height(localDims.heightPixels.dp.times(0.09f))
        .padding(4.dp),
        shape = RectangleShape,
        border = BorderStroke(1.dp, Color.DarkGray)) {

        // 수직 스크롤 목록 생성
        LazyColumn(modifier = Modifier.padding(3.dp)) {
            item {
                Text(text = cleanDescription)
            }
        }
    }

    Row(modifier = Modifier.padding(top = 6.dp),
        horizontalArrangement = Arrangement.SpaceAround) {

        // 클릭 시 MBook 객체를 생성
        RoundedButton(label = "저장"){
            val book = MBook(
                title = bookData.title.toString(),
                authors = bookData.authors.toString(),
                description = bookData.description.toString(),
                categories = bookData.categories.toString(),
                notes = "",
                photoUrl = bookData.imageLinks.thumbnail,
                publishedDate = bookData.publishedDate,
                pageCount = bookData.pageCount.toString(),
                rating = 0.0,
                googleBookId = googleBookId,
                userId = FirebaseAuth.getInstance().currentUser?.uid.toString())
            saveToFirebase(book, navController = navController)
        }
        Spacer(modifier = Modifier.width(25.dp))

        // 취소 버튼 - 이전 화면으로 돌아가는 작업 수행
        RoundedButton(label = "뒤로가기"){
            navController.popBackStack()
        }
    }

}

// Firebase에 MBook 객체를 저장하는 함수
fun saveToFirebase(book: MBook, navController: NavController) {
    val db = FirebaseFirestore.getInstance()
    val dbCollection = db.collection("books")

    // MBook 객체가 비어있지 않은 경우에만 Firebase에 저장
    if (book.toString().isNotEmpty()) {
        dbCollection.add(book)
            .addOnSuccessListener { documentRef ->
                val docId = documentRef.id
                dbCollection.document(docId)
                    .update(hashMapOf("id" to docId) as Map<String, Any>)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) { // 저장 성공 시 뒤로가기
                            navController.popBackStack()
                        }
                    }.addOnFailureListener {
                        Log.w("Error", "saveToFirebase: Error updating doc", it )
                    }
            }
    } else {

    }
}
