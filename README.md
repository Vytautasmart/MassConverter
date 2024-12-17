# Length Converter

Length Converter is an Android application that allows users to convert values between different units of length. This intuitive and user-friendly app is built using \*\*Jetpack Compose\*\*, providing a modern and responsive user interface.

## Features

- **Unit Conversion**: Supports conversion between multiple units of length (e.g., meters, kilometers, miles, inches, etc.).
- **Interactive UI**: Users can input a length, select the input unit and the desired output unit, and view the converted result instantly.
- **Error Handling**: Provides helpful error messages for invalid inputs (e.g., negative values, missing unit selections).
- **Material 3 Design**: Adheres to the latest Material Design guidelines for a clean and polished look.

---

## Application Architecture and Design Choices

### **1. State Management**

- State variables (`remember`, `mutableStateOf`) are used to manage user inputs and selections.
- State recomposition ensures smooth updates to the UI when the user interacts with the app.

### **2. Error Handling**

The app validates user inputs and provides helpful feedback via `Toast` messages, ensuring that:

- Negative lengths are not allowed.
- Unit selections are mandatory for conversion.
- The app gracefully handles invalid or missing inputs.

### **3. Reusability and Modularity**

- Dropdown menus for input and output units are built as reusable composable functions.
- The conversion logic is separated from the UI, enhancing maintainability. To add a new unit,

in the LengthUnits.kt file, add a new Length unit to the list, specify the name and the value.

The value shold be a single unit, converted to inches in order to perfor the correct calculation.

---

## How to Use

### **Installation**

1. Clone or download the repository to your local machine.
2. Open the project in **Android Studio**.
3. Connect an Android device or start an emulator.
4. Build and run the project.

### **Using the App**

1. **Input Length**: Enter a numeric value in the text field.
2. **Select Units**:
- Tap the "Select Input Unit" dropdown menu and choose the unit of the input value.
- Tap the "Select Output Unit" dropdown menu and choose the desired output unit.

3. **Convert**: Tap the Convert button. The converted value will be displayed below the button in the format:

## Possible Improvements

1. **Unit Customization**: Allow users to add or remove custom units.
2. **History**: Save and display a history of conversions for user reference.
3. **Dark Mode**: Implement support for dark theme to reduce eye strain.
4. **Search Bar**: Add a search bar so that the units can be easily found.
5. **Screen Rotation**:
