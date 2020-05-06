package com.example.twigaroll.home

import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.NavHostFragment

class TwiGarollNavHostFragment : NavHostFragment() {
    override fun createFragmentNavigator(): Navigator<out FragmentNavigator.Destination> {
        return TwiGarollNaivgator(requireContext(), childFragmentManager, id)
    }
}