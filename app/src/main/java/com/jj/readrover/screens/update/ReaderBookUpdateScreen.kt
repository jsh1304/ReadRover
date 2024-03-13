package com.jj.readrover.screens.update

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.jj.readrover.R
import com.jj.readrover.components.InputField
import com.jj.readrover.components.RatingBar
import com.jj.readrover.components.ReaderAppBar
import com.jj.readrover.components.RoundedButton
import com.jj.readrover.data.DataOrException
import com.jj.readrover.model.MBook
import com.jj.readrover.navigation.ReaderScreens
import com.jj.readrover.screens.home.HomeScreenViewModel
import com.jj.readrover.utils.formatDate

// 책 업데이트 화면 정의
@ExperimentalComposeUiApi
@Composable
fun BookUpdateScreen(
    navController: NavHostController,
    bookItemId: String,
    viewModel: HomeScreenViewModel = hiltViewModel(),
) {


    Scaffold(topBar = {
        ReaderAppBar(title = "책 업데이트",
            icon = Icons.Default.ArrowBack,
            showProfile = false,
            navController = navController)
    }) {
        val bookInfo = produceState<DataOrException<List<MBook>,
                Boolean,
                Exception>>(initialValue = DataOrException(data = emptyList(),
            true, Exception(""))) {

            value = viewModel.data.value

        }.value

        Surface(modifier = Modifier
            .fillMaxSize()
            .padding(3.dp)) {
            Column(
                modifier = Modifier.padding(top = 3.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Log.d("INFO", "BookUpdateScreen: ${viewModel.data.value.data.toString()}")

                if (bookInfo.loading == true) {
                    LinearProgressIndicator()
                    bookInfo.loading = false
                } else { // 데이터 로딩이 완료된 경우 책 정보 표시
                    // 책 정보를 표시하는 서피스 컴포넌트
                    Surface(modifier = Modifier
                        .padding(2.dp)
                        .fillMaxWidth(),
                        shape = CircleShape,
                        elevation = 4.dp) {
                        // 책 정보를 표시하는 컴포넌트 호출
                        ShowBookUpdate(bookInfo = viewModel.data.value, bookItemId = bookItemId)
                    }
                    // 해당 아이템 ID에 해당하는 책을 찾아서 간단한 폼을 표시하는 컴포넌트 호출
                    ShowSimpleForm(book = viewModel.data.value.data?.first { mBook ->
                        mBook.googleBookId == bookItemId
                    }!!, navController)
                }

            }
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun ShowSimpleForm(book: MBook, navController: NavHostController) {

    val context = LocalContext.current

    // 사용자의 노트 텍스트를 저장하기 위한 상태 변수
    val notesText = remember {
        mutableStateOf(book.notes ?: "")
    }

    // 독서 시작 및 완료 상태를 저장하는 상태 변수
    val isStartedReading = remember {
        mutableStateOf(false)
    }
    val isFinishedReading = remember {
        mutableStateOf(false)
    }

    // 책 평가 점수 저장하는 상태 변수
    val ratingVal = remember {
        mutableStateOf(0)
    }

    // SimpleForm을 호출하여 사용자 입력을 받음
    SimpleForm(defaultValue = if (book.notes.toString().isNotEmpty()) book.notes.toString()
            else "책에 대한 의견이 없습니다."){ note ->
        notesText.value = note
    }

    // 독서 시작 및 완료 버튼 행
    Row(modifier = Modifier.padding(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start) {
        // 독서 시작 버튼
        TextButton(onClick = { isStartedReading.value = true },
                enabled = book.startedReading == null) {
            if (book.startedReading == null) {
                if (!isStartedReading.value) {
                    Text(text = "독서 시작")
                } else {
                    Text(text = "독서 진행중",
                        modifier = Modifier.alpha(0.6f),
                        color = Color.Red.copy(alpha = 0.5f))
                }
            } else{
                Text(text = "독서 시작: ${formatDate(book.startedReading!!)}")
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        // 독서 완료 버튼
        TextButton(onClick = { isFinishedReading.value = true },
                enabled = book.finishedReading == null) {
            if (book.finishedReading == null) {
                if (!isFinishedReading.value) {
                    Text(text = "읽음 표시")
                } else {
                    Text(text = "독서 완료")
                }
            } else {
                Text(text = "독서 완료: ${formatDate(book.finishedReading!!)}")
            }
        }
    }
    // "평가" 텍스트를 표시하고 아래에 평점을 표시하는 부분
    Text(text = "평가", modifier = Modifier.padding(bottom = 3.dp))

    // 책의 평점을 가져와서 RatingBar로 표시
    book.rating?.toInt()?.let { // 책의 평점이 null이 아닌 경우
        RatingBar(rating = it) { rating -> // RatingBar 컴포저블 호출
            ratingVal.value = rating // 선택된 평점을 ratingVal에 저장
        }
    }
// 하단 여백 추가
    Spacer(modifier = Modifier.padding(bottom = 15.dp))

// 책 정보 업데이트를 위한 로우
    Row {
        // 책의 노트, 평점이 변경되었는지 확인
        val changedNotes = book.notes != notesText.value
        val changedRating = book.rating?.toInt() != ratingVal.value
        // 독서 시작 및 완료 타임스탬프 설정
        val isFinishedTimeStamp = if(isFinishedReading.value) Timestamp.now() else book.finishedReading
        val isStartedTimestamp = if (isStartedReading.value) Timestamp.now() else book.startedReading

        // 책 정보가 업데이트되었는지 여부를 확인
        val bookUpdate = changedNotes || changedRating || isFinishedReading.value || isStartedReading.value

        // 업데이트할 책 정보 맵 생성
        val bookToUpdate = hashMapOf(
            "finished_reading_at" to isFinishedTimeStamp,
            "started_reading_at" to isStartedTimestamp,
            "rating" to ratingVal.value,
            "notes" to notesText.value
        ).toMap()

        // 수정 버튼
        RoundedButton(label = "수정") {
            if (bookUpdate) { // 책 정보가 변경되었을 경우
                FirebaseFirestore.getInstance() // Firestore 인스턴스 가져오기
                    .collection("books") // "books" 컬렉션 참조
                    .document(book.id!!) // 해당 책 문서 참조
                    .update(bookToUpdate) // 업데이트할 정보로 업데이트 수행
                    .addOnCompleteListener { task ->
                        showToast(context, "책 업데이트 완료")
                        navController.navigate(ReaderScreens.ReaderHomeScreen.name)
                        //Log.d("TAG", "ShowSimpleForm: ${task.result.toString()}") // 업데이트 성공 로그
                    }.addOnFailureListener { // 실패 리스너
                        Log.w("Error", "document 업데이트 에러", it) // 에러 로그
                    }
            }
        }
        // 수정 버튼과 간격을 주기 위한 여백
        Spacer(modifier = Modifier.width(100.dp))
        // 다이얼로그를 열거나 닫기 위한 상태 변수 생성
        val openDialog = remember {
            mutableStateOf(false)
        }

        // 다이얼로그가 열려 있는 경우에만 ShowAlertDialog를 호출하여 다이얼로그 표시
        if (openDialog.value) {
            ShowAlertDialog(
                message = stringResource(id = R.string.sure) + "\n" +
                        stringResource(id = R.string.action), // 다이얼로그에 표시할 메시지
                openDialog = openDialog, // 다이얼로그 열기/닫기 상태
            ) {
                // "Yes" 버튼을 눌렀을 때 실행할 코드
                FirebaseFirestore.getInstance() // Firestore 인스턴스 가져오기
                    .collection("books") // "books" 컬렉션 참조
                    .document(book.id!!) // 해당 책 문서 참조
                    .delete() // 문서 삭제
                    .addOnCompleteListener { // 작업 완료 리스너
                        if (it.isSuccessful) { // 작업이 성공한 경우
                            openDialog.value = false // 다이얼로그 닫기
                            navController.navigate(ReaderScreens.ReaderHomeScreen.name) // 홈 화면으로 이동
                        }
                    }
            }
        }

        // "삭제" 버튼
        RoundedButton(label = "삭제") {
            // 삭제 버튼을 눌렀을 때 다이얼로그 열기
            openDialog.value = true
        }
    }

}

@Composable
fun ShowAlertDialog(
    message: String, // 다이얼로그에 표시할 메시지
    openDialog: MutableState<Boolean>, // 다이얼로그를 열거나 닫기 위한 상태
    onYesPressed: () -> Unit // "Yes" 버튼을 눌렀을 때 호출할 콜백 함수
) {
    if (openDialog.value) { // 다이얼로그가 열려 있는지 확인
        AlertDialog(
            onDismissRequest = { openDialog.value = false }, // 다이얼로그 닫기 요청 시 상태 변경
            title = { Text(text = "책 삭제") }, // 다이얼로그 제목
            text = { Text(text = message) }, // 다이얼로그 메시지
            buttons = {
                // 버튼 레이아웃
                Row(
                    modifier = Modifier.padding(all = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    // "Yes" 버튼
                    TextButton(onClick = { onYesPressed.invoke() }) {
                        Text(text = "네") // 버튼 텍스트
                    }
                    // "No" 버튼
                    TextButton(onClick = { openDialog.value = false }) {
                        Text(text = "아니오") // 버튼 텍스트
                    }
                }
            }
        )
    }
}

fun showToast(context: Context, msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_LONG)
        .show()
}

@ExperimentalComposeUiApi
@Composable
fun SimpleForm(
    modifier: Modifier = Modifier, // Modifier를 설정할 수 있는 매개변수
    loading: Boolean = false, // 로딩 상태를 나타내는 매개변수
    defaultValue: String = "최고의 책", // 기본값 설정
    onSearch: (String) -> Unit, // 검색 이벤트를 처리하는 콜백 함수
) {
    // 사용자의 입력을 저장하는 상태 변수
    val textFieldValue = rememberSaveable { mutableStateOf(defaultValue) }
    // 키보드 컨트롤러 가져오기
    val keyboardController = LocalSoftwareKeyboardController.current
    // 입력값이 유효한지 여부를 저장하는 상태 변수
    val valid = remember(textFieldValue.value) { textFieldValue.value.trim().isNotEmpty() }

    // 입력 필드를 구성
    InputField(
        modifier
            .fillMaxWidth()
            .height(140.dp)
            .padding(3.dp)
            .background(Color.White, CircleShape)
            .padding(horizontal = 20.dp, vertical = 12.dp),
        valueState = textFieldValue,
        labelId = "당신의 의견을 작성하세요.", // 라벨 텍스트
        enabled = true, // 활성화 여부
        onAction = KeyboardActions{
            // 입력값이 유효하면 검색 이벤트 호출 및 키보드 숨김
            if(!valid) return@KeyboardActions
            onSearch(textFieldValue.value.trim())
            keyboardController?.hide()
        }
    )
}

// 책 정보를 표시하는 함수
@Composable
fun ShowBookUpdate(
    bookInfo: DataOrException<List<MBook>, Boolean, Exception>, // 책 정보와 예외를 가진 상태값
    bookItemId: String, // 업데이트할 책의 아이템 ID
) {
    Row() {
        Spacer(modifier = Modifier.width(43.dp))

        // 책 정보가 null이 아닌 경우
        if (bookInfo.data != null) {
            Column(modifier = Modifier.padding(4.dp), verticalArrangement = Arrangement.Center
            ) {
                // 해당 아이템 ID와 일치하는 책 정보를 찾아 CardListItem으로 전달
                CardListItem(book = bookInfo.data!!.first { mBook ->
                    mBook.googleBookId == bookItemId

                }, onPressedDetails = {})

            }
        }
    }
}


// 책 정보를 표시하는 카드 아이템
@Composable
fun CardListItem(
    book: MBook, // 표시할 책 정보
    onPressedDetails: () -> Unit, // 상세 정보 버튼 클릭 시 호출할 콜백 함수
) {
    Card(modifier = Modifier
        .padding(
            start = 4.dp,
            end = 4.dp,
            top = 4.dp,
            bottom = 8.dp)
        .clip(RoundedCornerShape(20.dp))
        .clickable { },
        elevation = 8.dp) {

        Row(horizontalArrangement = Arrangement.Start) {
            Image(painter = rememberImagePainter(data = book.photoUrl.toString()),
                contentDescription = null,
                modifier = Modifier
                    .height(100.dp)
                    .width(120.dp)
                    .padding(4.dp)
                    .clip(RoundedCornerShape(topStart = 120.dp,
                        topEnd = 20.dp,
                        bottomEnd = 0.dp,
                        bottomStart = 0.dp)
                    ))
            Column {
                Text(text = book.title.toString(),
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp)
                        .width(120.dp),
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis)

                Text(text = book.authors.toString(),
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(start = 8.dp,
                        end = 8.dp,
                        top = 3.dp,
                        bottom = 0.dp))

                Text(text = book.publishedDate.toString(),
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(start = 8.dp,
                        end = 8.dp,
                        top = 0.dp,
                        bottom = 8.dp))
            }
        }

    }
}
