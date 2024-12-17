package com.uog.massconverter

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
import com.uog.massconverter.ui.theme.MassConverterTheme


class MainActivity : ComponentActivity() {


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
        var inputMassUnit by remember { mutableStateOf<MassUnit?>(null) }
        var outputMassUnit by remember { mutableStateOf<MassUnit?>(null) }
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
            DropdownInputMass(
                units = massUnits,
                selectedMassUnit = inputMassUnit,
                onUnitSelected = {inputMassUnit = it})

            DropdownOutputMass(
                units = massUnits,
                selectedMassUnit = outputMassUnit,
                onUnitSelected = {outputMassUnit = it})

            Spacer(modifier = Modifier.width(16.dp))
            Button(modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally),
                onClick =  {
                val inputValue = massEntered.toDoubleOrNull() ?: 0.0
                if (inputValue> 0 && inputMassUnit != null && outputMassUnit != null) {

                    // Performs the conversion
                    convertedValue = (inputValue * inputMassUnit!!.value) / outputMassUnit!!.value  //convertedValue = (massEntered * inputMassUnit)/outputMassUnit

                } else if (inputValue < 0) {
                    Toast.makeText(context, "Mass must be greater than 0",
                        Toast.LENGTH_SHORT).show()
                } else if (inputMassUnit == null) {
                    Toast.makeText(context, "Please select an input Mass Unit",
                        Toast.LENGTH_SHORT).show()
                } else if (outputMassUnit == null) {
                    Toast.makeText(context, "Please select an output Mass Unit",
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
                    text = "$massEntered ${inputMassUnit?.name}s is equal to " +
                            "${formatConvertedValue(convertedValue)} ${outputMassUnit?.name}s"
                )
            }
        }
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
fun DropdownInputMass(
    units: List<MassUnit>,
    selectedMassUnit: MassUnit?,
    onUnitSelected: (MassUnit) -> Unit) {

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
                value = selectedMassUnit?.name ?: "",
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

/**
 * */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownOutputMass(
    units: List<MassUnit>,
    selectedMassUnit: MassUnit?,
    onUnitSelected: (MassUnit) -> Unit) {

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
                value = selectedMassUnit?.name ?: "",
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

fun formatConvertedValue(value: Double): String {
    return if (value >= 0.01 || value <= -0.01) {
        String.format("%.2f", value)
    } else {
        value.toString()
    }
}

