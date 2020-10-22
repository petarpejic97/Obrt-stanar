package com.example.obrtstanar.Fragmenti

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.example.obrtstanar.Klase.Controllers.MapController
import com.example.obrtstanar.Klase.ObrtStanar
import com.example.obrtstanar.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker

class OurBuildings : Fragment(),AdapterView.OnItemSelectedListener,OnMapReadyCallback {
    lateinit var rootView : View
    lateinit var spinner : Spinner
    private lateinit var mapController : MapController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_our_buildings, container, false)

        initVariable()

        return rootView
    }

    private fun initVariable(){
        spinner = rootView.findViewById(R.id.cities_spinner)
        spinner.onItemSelectedListener = this
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        ArrayAdapter.createFromResource(ObrtStanar.ApplicationContext, R.array.cities_array, android.R.layout.simple_spinner_item).also {
                adapter -> adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onItemSelected(parent: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
        changeView(parent?.getItemAtPosition(position).toString())
    }
    private fun changeView(city : String){
        when(city){
            "Vinkovci"->{
                mapController.setMapView(LatLng(45.290951, 18.804829),12.5F)
            }
            "Slavonski Brod"->{
                mapController.setMapView(LatLng(45.158743,18.0209415),12.0F)
            }
            "Vukovar"->{
                mapController.setMapView(LatLng(45.3435227,18.9988763),13.5F)
            }
            "Stara Gradiška"->{
                mapController.setMapView(LatLng(45.1494648,17.2425013),15.0F)
            }
            "Ilok"->{
                mapController.setMapView(LatLng(45.22269,19.3754718),14.0F)
            }
            "Okučani"->{
                mapController.setMapView(LatLng(45.263817, 17.205661),13.5F)

            }
            "Zaton"->{
                mapController.setMapView(LatLng(44.2190968,15.1718823),14.5F)
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        mapController =
            MapController(googleMap!!)
        setMarkers()
    }

    private fun setMarkers(){
        setMarkersForVinkovci()
        setMarkersForStaraGradiska()
        setMarkersForIlok()
        setMarkersForSlavonskiBrod()
        setMarkersForVukovar()
        setMarkersForOkucani()
        setMarkersForZaton()
    }
    private fun setMarkersForVinkovci(){
        mapController.setMarker(LatLng(45.2870627,18.7999971))
        mapController.setMarkerTitle("Trg dr. Franje Tuđmana 3A")
        mapController.setMarker(LatLng(45.2858003,18.8090755))
        mapController.setMarkerTitle("Ulica Stjepana Radića 35A")
        mapController.setMarker(LatLng(45.2935533,18.7944487))
        mapController.setMarkerTitle("Lapovačka ulica 32")
        mapController.setMarker(LatLng(45.2899998,18.771498))
        mapController.setMarkerTitle("Ulica Alojzija Stepinca 80")
        mapController.setMarker(LatLng(45.2963761,18.7952923))
        mapController.setMarkerTitle("Ulica Josipa Jurja Strossmayera 9")
        mapController.setMarker(LatLng(45.2968032,18.799254))
        mapController.setMarkerTitle("Ulica Kneza Mislava 26")
        mapController.setMarker(LatLng(45.2963833,18.7990291))
        mapController.setMarkerTitle("Ulica Kneza Mislava 7")
        mapController.setMarker(LatLng(45.2934906,18.7942417))
        mapController.setMarkerTitle("Lapovačka ulica 34")
        mapController.setMarker(LatLng(45.2949798,18.7984358))
        mapController.setMarkerTitle("Lapovačka ulica 14")
        mapController.setMarker(LatLng(45.2951426,18.7983751))
        mapController.setMarkerTitle("Lapovačka ulica 16")
        mapController.setMarker(LatLng(45.2945402,18.797963))
        mapController.setMarkerTitle("Lapovačka ulica 18")
        mapController.setMarker(LatLng(45.294486,18.7977503))
        mapController.setMarkerTitle("Lapovačka ulica 20")
        mapController.setMarker(LatLng(45.2944322,18.7975155))
        mapController.setMarkerTitle("Lapovačka ulica 22")
        mapController.setMarker(LatLng(45.2935115,18.7938561))
        mapController.setMarkerTitle("Lapovačka ulica 36")
        mapController.setMarker(LatLng(45.2902667,18.8084559))
        mapController.setMarkerTitle("Ulica Hrvatskih Kraljeva 28")
        mapController.setMarker(LatLng(45.2934851,18.8033632))
        mapController.setMarkerTitle("Ulica Vladimira Nazora 22")
        mapController.setMarker(LatLng(45.2938957,18.8035562))
        mapController.setMarkerTitle("Ulica Vladimira Nazora 24")
        mapController.setMarker(LatLng(45.2968145,18.7975046))
        mapController.setMarkerTitle("Ohridska ulica 4")
        mapController.setMarker(LatLng(45.296787,18.7974329))
        mapController.setMarkerTitle("Ohridska ulica 6")
        mapController.setMarker(LatLng(45.2967708,18.7974248))
        mapController.setMarkerTitle("Ohridska ulica 8")
        mapController.setMarker(LatLng(45.2964321,18.7960247))
        mapController.setMarkerTitle("Ohridska ulica 10")
        mapController.setMarker(LatLng(45.2957309,18.7921304))
        mapController.setMarkerTitle("Ohridska ulica 14")
        mapController.setMarker(LatLng(45.2964634,18.7981046))
        mapController.setMarkerTitle("Ohridska ulica 5")
        mapController.setMarker(LatLng(45.2964231,18.7977907))
        mapController.setMarkerTitle("Ohridska ulica 7")
        mapController.setMarker(LatLng(45.2982637,18.8018626))
        mapController.setMarkerTitle("Ulica Ante Starčevića 70")
        mapController.setMarker(LatLng(45.2984363,18.8019615))
        mapController.setMarkerTitle("Ulica Ante Starčevića 72")
        mapController.setMarker(LatLng(45.2982939,18.8022799))
        mapController.setMarkerTitle("Ulica Ante Starčevića 62")
        mapController.setMarker(LatLng(45.297953,18.8023019))
        mapController.setMarkerTitle("Ulica Ante Starčevića 64")
        mapController.setMarker(LatLng(45.2939719,18.8007532))
        mapController.setMarkerTitle("Ulica Ante Starčevića 7")
        mapController.setMarker(LatLng(45.2939108,18.8003827))
        mapController.setMarkerTitle("Ulica Ante Starčevića 9")
        mapController.setMarker(LatLng(45.2944498,18.8002101))
        mapController.setMarkerTitle("Ulica Ante Starčevića 15")
        mapController.setMarker(LatLng(45.2944087,18.8004666))
        mapController.setMarkerTitle("Ulica Ante Starčevića 17")
        mapController.setMarker(LatLng(45.2963349,18.8015145))
        mapController.setMarkerTitle("Ulica Ante Starčevića 19")
        mapController.setMarker(LatLng(45.2943211,18.7963315))
        mapController.setMarkerTitle("Ulica Josipa Jurja Strossmayera 1")
        mapController.setMarker(LatLng(45.2945814,18.7961411))
        mapController.setMarkerTitle("Ulica Josipa Jurja Strossmayera 3")
        mapController.setMarker(LatLng(45.2947629,18.7960655))
        mapController.setMarkerTitle("Ulica Josipa Jurja Strossmayera 5")
        mapController.setMarker(LatLng(45.295952,18.7962379))
        mapController.setMarkerTitle("Ulica Josipa Jurja Strossmayera 10")
        mapController.setMarker(LatLng(45.2957422,18.7962586))
        mapController.setMarkerTitle("Ulica Josipa Jurja Strossmayera 8")
        mapController.setMarker(LatLng(45.2967335,18.7951983))
        mapController.setMarkerTitle("Ulica Josipa Jurja Strossmayera 11")
        mapController.setMarker(LatLng(45.5625147,18.6724601))
        mapController.setMarkerTitle("Ulica Josipa Jurja Strossmayera 14")
        mapController.setMarker(LatLng(45.2765198,18.7916536))
        mapController.setMarkerTitle("Ulica Antuna Zrinšeka 15")
        mapController.setMarker(LatLng(45.2974872,18.8038399))
        mapController.setMarkerTitle("Ulica Ivana Mažuranića 10")
        mapController.setMarker(LatLng(45.2974408,18.8040482))
        mapController.setMarkerTitle("Ulica Ivana Mažuranića 12")
        mapController.setMarker(LatLng(45.2778029,18.7753905))
        mapController.setMarkerTitle("Tenina 3")
        mapController.setMarker(LatLng(45.2929513,18.7915819))
        mapController.setMarkerTitle("Ulica Matice hrvatske 6")
        mapController.setMarker(LatLng(45.2951145,18.7969124))
        mapController.setMarkerTitle("A. B. Šimića 13")
        mapController.setMarker(LatLng(45.2951263,18.7966702))
        mapController.setMarkerTitle("A. B. Šimića 15")
        mapController.setMarker(LatLng(45.2942642,18.7944609))
        mapController.setMarkerTitle("A. B. Šimića 21")
        mapController.setMarker(LatLng(45.2953745,18.8041146))
        mapController.setMarkerTitle("Trg Josipa Runjanina 14")
        mapController.setMarker(LatLng(45.2953026,18.8043318))
        mapController.setMarkerTitle("Trg Josipa Runjanina 15")
        mapController.setMarker(LatLng(45.2855017,18.8002875))
        mapController.setMarkerTitle("Ulica Josipa Lovretića 2")
        mapController.setMarker(LatLng(45.2828359,18.8028187))
        mapController.setMarkerTitle("Ulica Josipa Lovretića 46")
        mapController.setMarker(LatLng(45.2892563,18.8124418))
        mapController.setMarkerTitle("Ulica Petra Zrinskog 12")
        mapController.setMarker(LatLng(45.2995676,18.8034878))
        mapController.setMarkerTitle("Ulica Andrije Hebranga 2A")
        mapController.setMarker(LatLng(45.2993873,18.8035526))
        mapController.setMarkerTitle("Ulica Andrije Hebranga 2B")
        mapController.setMarker(LatLng(45.2868868,18.802867))
        mapController.setMarkerTitle("Vatrogasna ulica 12")
        mapController.setMarker(LatLng(45.2869288,18.8031315))
        mapController.setMarkerTitle("Vatrogasna ulica 16")
        mapController.setMarker(LatLng(45.287464,18.8010325))
        mapController.setMarkerTitle("Duga ulica 1")
        mapController.setMarker(LatLng(45.2886441,18.7968352))
        mapController.setMarkerTitle("Duga ulica 51")
        mapController.setMarker(LatLng(45.2925251,18.8016403))
        mapController.setMarkerTitle("Ulica Jurja Dalmatinca 17")
        mapController.setMarker(LatLng(45.2924601,18.8018149))
        mapController.setMarkerTitle("Ulica Jurja Dalmatinca 19")
        mapController.setMarker(LatLng(45.2892745,18.8036347))
        mapController.setMarkerTitle("Ulica kralja Zvonimira 4")
        mapController.setMarker(LatLng(45.2974092,18.7973282))
        mapController.setMarkerTitle("Ulica Slavka Jankovića 7")
        mapController.setMarker(LatLng(45.2941232,18.8157119))
        mapController.setMarkerTitle("Ulica Nikole teske 39B")
        mapController.setMarker(LatLng(45.2880185,18.7965038))
        mapController.setMarkerTitle("Šetalište Dionizija Švagelja 5")
    }
    private fun setMarkersForIlok(){
        mapController.setMarker(LatLng(45.2242869,19.3633258))
        mapController.setMarkerTitle("Ulica Vlatka Kraljevića 1")
        mapController.setMarker(LatLng(45.2242338,19.3632475))
        mapController.setMarkerTitle("Ulica Vlatka Kraljevića 1A")
        mapController.setMarker(LatLng(45.2246931,19.3631997))
        mapController.setMarkerTitle("Ulica Vlatka Kraljevića 2")
        mapController.setMarker(LatLng(45.2242338,19.3632475))
        mapController.setMarkerTitle("Ulica Vlatka Kraljevića 4")
        mapController.setMarker(LatLng(45.2249696,19.3642197))
        mapController.setMarkerTitle("Ulica Vlatka Kraljevića 14")
        mapController.setMarker(LatLng(45.2249696,19.3642197))
        mapController.setMarkerTitle("Ulica Vlatka Kraljevića 16")
        mapController.setMarker(LatLng(45.224403,19.3621628))
        mapController.setMarkerTitle("Ulica dr. Franje Tuđmana 60")
        mapController.setMarker(LatLng(45.2244283,19.362079))
        mapController.setMarkerTitle("Ulica dr. Franje Tuđmana 62")
        mapController.setMarker(LatLng(45.22269,19.3754718))
        mapController.setMarkerTitle("Trg žrtava Domovinskog rata 13")
    }
    private fun setMarkersForStaraGradiska(){
        mapController.setMarker(LatLng(45.1508759,17.2441388))//5
        mapController.setMarkerTitle("Cvijetni trg 5")
        mapController.setMarker(LatLng(45.150869, 17.244788))//6
        mapController.setMarkerTitle("Cvijetni trg 6")
        mapController.setMarker(LatLng(45.1508869,17.2439698))//7
        mapController.setMarkerTitle("Cvijetni trg 7")
        mapController.setMarker(LatLng(45.150890, 17.244372))//8
        mapController.setMarkerTitle("Cvijetni trg 8")
        mapController.setMarker(LatLng(45.1508183,17.241907))//9
        mapController.setMarkerTitle("Cvijetni trg 9")
        mapController.setMarker(LatLng(45.149961, 17.244115))//13
        mapController.setMarkerTitle("Cvijetni trg 13")
        mapController.setMarker(LatLng(45.149961, 17.244115))
        mapController.setMarkerTitle("Cvijetni trg 13")
        mapController.setMarker(LatLng(45.149961, 17.244115))
        mapController.setMarkerTitle("Cvijetni trg 13")
        mapController.setMarker(LatLng(45.149961, 17.244115))
        mapController.setMarkerTitle("Cvijetni trg 13")
        mapController.setMarker(LatLng(45.149961, 17.244115))
        mapController.setMarkerTitle("Cvijetni trg 13")
        mapController.setMarker(LatLng(45.1508466,17.2421267))
        mapController.setMarkerTitle("Ulica kralja Petra Svačića 1")
        mapController.setMarker(LatLng(45.1508697,17.2423349))
        mapController.setMarkerTitle("Ulica kralja Petra Svačića 3")
        mapController.setMarker(LatLng(45.1506249,17.2402094))
        mapController.setMarkerTitle("Ulica kralja Petra Svačića 4")
        mapController.setMarker(LatLng(45.1507371,17.2404345))
        mapController.setMarkerTitle("Ulica kralja Petra Svačića 5")
        mapController.setMarker(LatLng(45.1506424,17.2405748))
        mapController.setMarkerTitle("Ulica kralja Petra Svačića 6")
        mapController.setMarker(LatLng(45.1507631,17.2406861))
        mapController.setMarkerTitle("Ulica kralja Petra Svačića 7")
        mapController.setMarker(LatLng(45.1506622,17.2409384))
        mapController.setMarkerTitle("Ulica kralja Petra Svačića 8")
        mapController.setMarker(LatLng(45.1507628,17.2409273))
        mapController.setMarkerTitle("Ulica kralja Petra Svačića 9")
        mapController.setMarker(LatLng(45.1507749,17.2411168))
        mapController.setMarkerTitle("Ulica kralja Petra Svačića 11")
        mapController.setMarker(LatLng(45.150799,17.2414581))
        mapController.setMarkerTitle("Ulica kralja Petra Svačića 13")
        mapController.setMarker(LatLng(45.1508899,17.2438278))
        mapController.setMarkerTitle("Ulica kralja Petra Svačića 15")
        mapController.setMarker(LatLng(45.1497474,17.2421614))
        mapController.setMarkerTitle("Ulica kralja Tomislava 1")
        mapController.setMarker(LatLng(45.149269,17.2424704))
        mapController.setMarkerTitle("Ulica kralja Tomislava 2")
        mapController.setMarker(LatLng(45.1499356,17.2426022))
        mapController.setMarkerTitle("Ulica kralja Tomislava 3")
        mapController.setMarker(LatLng(45.1492814,17.2428544))
        mapController.setMarkerTitle("Ulica kralja Tomislava 4")
        mapController.setMarker(LatLng(45.1499345,17.2427612))
        mapController.setMarkerTitle("Ulica kralja Tomislava 5")
        mapController.setMarker(LatLng(45.1496116,17.2432678))
        mapController.setMarkerTitle("Ulica kralja Tomislava 6")
        mapController.setMarker(LatLng(45.1499425,17.2429235))
        mapController.setMarkerTitle("Ulica kralja Tomislava 7")
        mapController.setMarker(LatLng(45.1499549,17.2430922))
        mapController.setMarkerTitle("Ulica kralja Tomislava 9")


    }
    private fun setMarkersForSlavonskiBrod(){
        mapController.setMarker(LatLng(45.1587392,18.0209468))
        mapController.setMarkerTitle("Ulica Nikole Zrinskog 65")
        mapController.setMarker(LatLng(45.1591551,18.0136354))
        mapController.setMarkerTitle("Ulica Matije Gubca 32")
    }
    private fun setMarkersForVukovar(){
        mapController.setMarker(LatLng(45.3560917,18.9993136))
        mapController.setMarkerTitle("Dunavska ulica 7")
        mapController.setMarker(LatLng(45.3536283,18.9961744))
        mapController.setMarkerTitle("Kardinala Alojzija Stepinca 9")
        mapController.setMarker(LatLng(45.3507346,19.0026501))
        mapController.setMarkerTitle("Trg Republike Hrvatske 2")

    }
    private fun setMarkersForOkucani(){
        mapController.setMarker(LatLng(45.270926,17.2042773))
        mapController.setMarkerTitle("Ulica Ante Starčevića 3")
        mapController.setMarker(LatLng(45.2863282,17.2056819))
        mapController.setMarkerTitle("Ulica Ante Starčevića 80")
        mapController.setMarker(LatLng(45.2564855,17.200145))
        mapController.setMarkerTitle("Ulica 121. Brigade HV11")
    }
    private fun setMarkersForZaton(){
        mapController.setMarker(LatLng(44.227008, 15.171243))
        mapController.setMarkerTitle("Dražnikova ulica 64A")
    }
}