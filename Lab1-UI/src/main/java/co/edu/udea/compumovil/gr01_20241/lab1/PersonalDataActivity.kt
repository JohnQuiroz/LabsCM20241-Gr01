package co.edu.udea.compumovil.gr01_20241.lab1

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.DatePicker
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import android.app.DatePickerDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.edu.udea.compumovil.gr01_20241.lab1.ui.theme.Labs20241Gr01Theme
import java.util.Calendar

class PersonalDataActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Labs20241Gr01Theme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(text = stringResource(id = R.string.title_activity_personal_data))
                            }
                        )
                    }
                ){
                    Container(
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Container(modifier: Modifier = Modifier) {
    val grades = listOf("Preescolar", "Primaria", "Secundaria", "Universidad")
    val default = 0
    var state by rememberSaveable { mutableStateOf(false) }
    var name by rememberSaveable { mutableStateOf("") }
    var lastname by rememberSaveable { mutableStateOf("") }
    var nameError by rememberSaveable { mutableStateOf(false) }
    var lastnameError by rememberSaveable { mutableStateOf(false) }
    var expanded by rememberSaveable { mutableStateOf(false) }
    var selectedGrade by rememberSaveable { mutableStateOf(grades[default]) }
    var mDate by rememberSaveable { mutableStateOf("") }
    var dateError by rememberSaveable { mutableStateOf(false) }
    val mContext = LocalContext.current
    val mYear: Int
    val mMonth: Int
    val mDay: Int
    val mCalendar = Calendar.getInstance()
    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)
    val mDatePicker = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            mDate = "$mDayOfMonth/${mMonth + 1}/$mYear"
        }, mYear, mMonth, mDay
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 60.dp, start = 20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 20.dp),
            value = name,
            onValueChange = {
                nameError = false
                name = it },
            label = { Text(stringResource(id = R.string.name_label) + "*") },
            textStyle = TextStyle(color = Color.DarkGray),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                keyboardType = KeyboardType.Text
            ),
            isError = nameError,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = null
                )
            }
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 20.dp),
            value = lastname,
            onValueChange = {
                lastnameError = false
                lastname = it },
            label = { Text(stringResource(id = R.string.lastname_label) + "*") },
            textStyle = TextStyle(color = Color.DarkGray),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                keyboardType = KeyboardType.Text
            ),
            isError = lastnameError,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = null
                )
            }
        )
        Row(
            modifier = Modifier
                .selectableGroup()
                .fillMaxWidth()
                .padding(end = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(3.dp)
            ){
                Icon(imageVector = Icons.Filled.People, contentDescription = null)
                Text(text = stringResource(id = R.string.sex_label))
            }
            LabelledRadioButton(
                label = stringResource(id = R.string.masculine_label),
                selected = state,
                onSelect = { state = true }
            )
            LabelledRadioButton(
                label = stringResource(id = R.string.femenine_label),
                selected = !state,
                onSelect = { state = false }
            )
        }
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 20.dp),
            value = mDate,
            onValueChange = { dateError = false },
            label = { Text(stringResource(id = R.string.date_label)) },
            textStyle = TextStyle(color = Color.DarkGray),
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { mDatePicker.show() }) {
                    Icon(
                        imageVector = Icons.Filled.EditCalendar,
                        contentDescription = null
                    )
                }
            }
        )
        ExposedDropdownMenuBox(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 20.dp, top = 10.dp),
            expanded = expanded,
            onExpandedChange = {expanded = !expanded}
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth(),
                readOnly = true,
                value = selectedGrade,
                onValueChange = {},
                label = { Text(stringResource(id = R.string.school_label)) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.School,
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                colors = ExposedDropdownMenuDefaults.textFieldColors()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                grades.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = { Text(text = selectionOption) },
                        onClick = {
                            selectedGrade = selectionOption
                            expanded = false
                        }
                    )
                }
            }
        }
        ElevatedButton(
            onClick = {
                nameError = name.isBlank()
                lastnameError = lastname.isBlank()
                dateError = mDate.isBlank()
            },
            modifier = Modifier
                .align(Alignment.End)
                .padding(end = 20.dp)
        ) {
            Text(text = stringResource(id = R.string.next))
        }
    }
}

@Composable
fun LabelledRadioButton(
    label: String,
    selected: Boolean,
    onSelect: (() -> Unit)?,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .height(56.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        RadioButton(
            selected = selected,
            onClick = onSelect,
            modifier = modifier
        )
        Text(
            text= label,
            style = MaterialTheme.typography.labelMedium.merge(),
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ContinerPreview() {
    Labs20241Gr01Theme {
        Container()
    }
}