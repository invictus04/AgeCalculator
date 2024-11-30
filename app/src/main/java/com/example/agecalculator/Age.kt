package com.example.agecalculator


import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import java.time.Duration
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgeCalculator() {
    val calendarState = rememberUseCaseState()
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var ageInMillis by remember { mutableStateOf<Long?>(null) }
    var yearsPassed by remember { mutableStateOf<String?>(null)}
    CalendarDialog(state = calendarState,
        config = CalendarConfig(
            monthSelection = true,
            yearSelection = true
        )  ,
        selection = CalendarSelection.Date{ date ->
          selectedDate = date
            val currentDate = LocalDate.now()
            val duration = Duration.between(selectedDate.atStartOfDay(),currentDate.atStartOfDay())
            ageInMillis = duration.toMillis()
            val period = Period.between(selectedDate,currentDate)
            yearsPassed = period.years.toString()

        })
    Column(
        Modifier
            .fillMaxSize()
            .padding(5.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Calculate Your",
                fontSize = 30.sp, modifier = Modifier.padding(16.dp))

        Box(modifier = Modifier
            .background(Color.Blue)
            .size(70.dp)) {

                Text(text = "Age",
                   color = Color.White,
                   fontWeight = FontWeight.Bold,
                   modifier = Modifier.align(Alignment.Center),
                   fontSize = 30.sp
                   )
            }

        Text(text = "in Minutes",
            Modifier.padding(20.dp),
            fontSize = 30.sp)


        OutlinedButton(onClick = { calendarState.show() }, modifier = Modifier.fillMaxWidth(0.85f)) {
            Text(text = "Select Date", fontSize = 20.sp, color = Color.DarkGray)
        }

        Text(text = selectedDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
            Modifier.padding(20.dp),
            fontSize = 20.sp)

        Text(text = "Selected Date",
            Modifier.padding(10.dp),
            fontSize = 20.sp)

        Text(text = ageInMillis.toString(),
            Modifier.padding(20.dp),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold)

        Text(text = yearsPassed.toString(),
            Modifier.padding(10.dp),
            fontSize = 20.sp)

        Text(text = "Your Age in Minutes",
            Modifier.padding(10.dp),
            fontSize = 20.sp)

    }
}



@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun AgeScreenPreview() {
    AgeCalculator()
}
