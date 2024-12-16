package com.uog.massconverter

import android.health.connect.datatypes.units.Mass

/*
val list  = mapOf(
    "Inch" to 1,
    "foot" to 12,
    "yard" to 36,
    "mile" to 63360
)
*/
data class MassUnit(val name: String, val value: Int, )

val massUnits = listOf(
    MassUnit("Inch", 1),
    MassUnit("Foot", 12),
    MassUnit("Yard", 36),
    MassUnit("Mile", 63360),
    MassUnit("Stick", 2),
    MassUnit("Hand", 4),
    MassUnit("Nautical Mile", 72960),
    MassUnit("League", 218880),
    MassUnit("Palm", 3),
    MassUnit("Span", 9),
    MassUnit("Cubit", 18),
    MassUnit("Link", 18),
    MassUnit("Shaftment", 6),
    MassUnit("Pace", 30),
    MassUnit("Grade", 60),
    MassUnit("Step", 60),
    MassUnit("Rope", 240),
    MassUnit("Ramsden's Chain", 1200),
    MassUnit("Roman mile", 60000),
    MassUnit("Ell", 45),
    MassUnit("Skein", 4320),
    MassUnit("Spindle", 518400),
    MassUnit("Rod", 198),
    MassUnit("Pole", 198),
    MassUnit("Perch", 198),
    MassUnit("Gunters chain", 792),
    MassUnit("Furlong", 7920),
    MassUnit("Fathom", 72),
    MassUnit("Shackle", 1080),
    MassUnit("Cable", 7200)
)