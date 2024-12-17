package com.uog.lengthconverter

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.uog.lengthconverter.ui.theme.LengthConverterTheme

/**
 * Main activity class for the Length Converter app.
 * It sets up the UI and initializes the length conversion functionality.
 * The app works by converting a selected unit lengthEntered,
 * take the input length, the output length, perform the conversion
 * and return the formatted value if all the fields have been entered correctly.
 * @author Vytautas Martuzas
 */

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LengthConverterTheme {
                LengthConverterApp()
                }
            }
        }

    @Composable
    fun LengthConverterApp() {
        var lengthEntered by remember { mutableStateOf("") }
        var inputLengthUnit by remember { mutableStateOf<LengthUnit?>(null) }
        var outputLengthUnit by remember { mutableStateOf<LengthUnit?>(null) }
        var convertedValue by remember { mutableDoubleStateOf(0.0) }


        val context = LocalContext.current
        Column(modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()) {

            Image(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)
                    .size(150.dp),
                painter = painterResource(id = R.drawable.ic_launcher),
                contentDescription = "App Icon")

            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(16.dp))
            /**
             * TextField for the number required to convert
             * */
            TextField(
                value = lengthEntered,
                onValueChange = {lengthEntered = it},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                label = { Text(text = "Enter Length")},
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.height(16.dp))
            DropdownInputLength(
                units = LengthUnits,
                selectedLengthUnit = inputLengthUnit,
                onUnitSelected = {inputLengthUnit = it})

            DropdownOutputLength(
                units = LengthUnits,
                selectedLengthUnit = outputLengthUnit,
                onUnitSelected = {outputLengthUnit = it})

            Spacer(modifier = Modifier.width(16.dp))
            /**
             * The function for conversion is baked into the button
             * */
            Button(modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally),
                onClick =  {
                val inputValue = lengthEntered.toDoubleOrNull() ?: 0.0
                if (inputValue> 0 && inputLengthUnit != null && outputLengthUnit != null) {

                    // Performs the conversion
                    convertedValue = (inputValue * inputLengthUnit!!.value) / outputLengthUnit!!.value
                    /**
                     * error handling, to inform on missing fields required to perform the conversion
                     * */
                } else if (inputValue < 0) {
                    Toast.makeText(context, "Length must be greater than 0",
                        Toast.LENGTH_SHORT).show()
                } else if (inputLengthUnit == null) {
                    Toast.makeText(context, "Please select an input Length Unit",
                        Toast.LENGTH_SHORT).show()
                } else if (outputLengthUnit == null) {
                    Toast.makeText(context, "Please select an output Length Unit",
                        Toast.LENGTH_SHORT).show()
                }

            }) {
                Text(text = "Convert")
            }
            if (convertedValue > 0) {
                Text(
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally),
                    text = "$lengthEntered ${inputLengthUnit?.name}s is equal to " +
                            "${formatConvertedValue(convertedValue)} ${outputLengthUnit?.name}s"
                )
            }
        }
    }


    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        LengthConverterTheme {
            LengthConverterApp()
        }
    }
}

/**
 * A dropdown menu for selecting input length units.
 *
 * @param units List of available length units.
 * @param selectedLengthUnit Currently selected length unit.
 * @param onUnitSelected Callback when a unit is selected.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownInputLength(
    units: List<LengthUnit>,
    selectedLengthUnit: LengthUnit?,
    onUnitSelected: (LengthUnit) -> Unit) {

    var isExpanded by remember { mutableStateOf(false) }

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
                label = { Text(text = "Select Input Unit")},
                modifier = Modifier.menuAnchor(),
                value = selectedLengthUnit?.name ?: "",
                onValueChange = { },
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)}
            )

            ExposedDropdownMenu(
                expanded = isExpanded,
                onDismissRequest = {isExpanded = false}) {
                units.forEach { unit ->
                    DropdownMenuItem(
                        text = { Text(text = unit.name)},
                        onClick = {
                            onUnitSelected(unit)
                            isExpanded = false},
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownOutputLength(
    units: List<LengthUnit>,
    selectedLengthUnit: LengthUnit?,
    onUnitSelected: (LengthUnit) -> Unit) {

    var isExpanded by remember { mutableStateOf(false) }

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
                label = { Text(text = "Select Output Unit")},
                modifier = Modifier.menuAnchor(),
                value = selectedLengthUnit?.name ?: "",
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)}
            )

            ExposedDropdownMenu(expanded = isExpanded, onDismissRequest = {isExpanded = false}) {
                units.forEach { unit ->
                    DropdownMenuItem(
                        text = { Text(text = unit.name)},
                        onClick = {
                            onUnitSelected(unit)
                            isExpanded = false},
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }
    }
}

/**
 * Formats the converted value to two decimal places or returns the raw value if insignificant.
 * For more precise conversions.
 *
 * @param value The value to be formatted.
 * @return The formatted string representation of the value.
 */

fun formatConvertedValue(value: Double): String {
    return if (value >= 0.01 || value <= -0.01) {
        String.format("%.2f", value)
    } else {
        value.toString()
    }
}

