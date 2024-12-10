package com.uog.massconverter

import android.icu.text.NumberFormat
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.uog.massconverter.ui.theme.MassConverterTheme


class MainActivity : ComponentActivity() {

    private val conversionToPounds : Double = 2.20462262 //lbs to kg
    private val conversionToStones : Double = 0.15747304 //lbs to kg
    private val gravity : Double = 9.81 //m/s^2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MassConverterTheme {
                WeightConverterApp()
                }
            }
        }

    @Composable
    fun WeightConverterApp() {
        var massEntered by remember { mutableStateOf("") }
        var inputMassUnit by remember { mutableStateOf("") }
        var outputMassUnit by remember { mutableStateOf("") }
        var convertedValue by remember { mutableStateOf("") }
        val numberFormat = remember {
            NumberFormat.getInstance().apply {
                maximumFractionDigits = 2
                minimumFractionDigits = 2
            }
        }

        val context = LocalContext.current
        Column(modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()) {

            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = massEntered,
                onValueChange = {massEntered = it},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                label = { Text(text = "Enter Mass")},
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.height(16.dp))

            DropdownInputMass()

            DropdownOutputMass()

            Spacer(modifier = Modifier.width(16.dp))

            Button(onClick =  {
                val mass = massEntered.toDoubleOrNull()

                convertedValue = (massEntered * inputMassUnit)/outputMassUnit

                if (mass != null && mass > 0) {

                } else {
                    Toast.makeText(context, "Mass must be greater than 0",
                        Toast.LENGTH_SHORT).show()
                }
            }) {
                Text(text = "Convert")

            }
        }
    }


    private fun converter(numberFormat: NumberFormat, mass: Double, inputUnit: String, outputUnit: String) : Array<String> {

        val conversionToBeReturned = arrayOf<String>("","","")

        val massInKg = when (inputUnit) {
            "kg" -> mass
            "lbs" -> mass / conversionToPounds
            "st" -> mass / conversionToStones
            else -> 0.0
        }

        val converted = when (outputUnit){
            "kg" -> "$massInKg kg"
            "lbs" -> "${numberFormat.format(mass * conversionToPounds)} lbs"
            "st" -> "${numberFormat.format(mass * conversionToStones)} stone"
            else -> ""
        }

        return conversionToBeReturned
    }


    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        MassConverterTheme {
            WeightConverterApp()
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownInputMass() {

    var isExpanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(list.keys.first())}

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = { isExpanded = !isExpanded}
        ){
            TextField(
                modifier = Modifier.menuAnchor(),
                value = selectedText,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)}
            )

            ExposedDropdownMenu(expanded = isExpanded, onDismissRequest = {isExpanded = false}) {
                list.keys.forEachIndexed { index, text ->
                    DropdownMenuItem(
                        text = { Text(text = text)},
                        onClick = {
                            selectedText = text
                            isExpanded = false},
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }
        val inputMassUnit = list[selectedText] ?: 0 // Default to 0 if not found

        Text(text = "Currently selected: $selectedText with value: $inputMassUnit")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownOutputMass() {

    var isExpanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(list.keys.first())}

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = { isExpanded = !isExpanded}
        ){
            TextField(
                modifier = Modifier.menuAnchor(),
                value = selectedText,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)}
            )

            ExposedDropdownMenu(expanded = isExpanded, onDismissRequest = {isExpanded = false}) {
                list.keys.forEachIndexed { index, text ->
                    DropdownMenuItem(
                        text = { Text(text = text)},
                        onClick = {
                            selectedText = text
                            isExpanded = false},
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }
        val outputMassUnit = list[selectedText] ?: 0 // Default to 0 if not found

        Text(text = "Currently selected: $selectedText with value: $outputMassUnit")
    }
}


