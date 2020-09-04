package com.example.obrtstanar.Fragmenti

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.obrtstanar.Klase.MapController
import com.example.obrtstanar.Klase.ObrtStanar
import com.example.obrtstanar.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng

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
                mapController.setMapView(LatLng(45.263817, 17.205661),14.0F)

            }
            "Zaton"->{
                mapController.setMapView(LatLng(44.2190968,15.1718823),14.5F)
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        mapController = MapController(googleMap!!)
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
        mapController.setMarker(LatLng(45.2995676,18.8034878))
        mapController.setMarker(LatLng(45.2993873,18.8035526))
        mapController.setMarker(LatLng(45.2934851,18.8033632))
        mapController.setMarker(LatLng(45.2869288,18.8031315))
        mapController.setMarker(LatLng(45.2892745,18.8036347))
        mapController.setMarker(LatLng(45.2828359,18.8028187))
        mapController.setMarker(LatLng(45.2924601,18.8018149))
        mapController.setMarker(LatLng(45.2974092,18.7973282))
        mapController.setMarker(LatLng(45.2807421,18.8050881))
        mapController.setMarker(LatLng(45.2941232,18.8157119))
        mapController.setMarker(LatLng(45.2944498,18.8002101))
        mapController.setMarker(LatLng(45.2944087,18.8004666))
        mapController.setMarker(LatLng(45.294376,18.8007491))
        mapController.setMarker(LatLng(45.2951145,18.7969124))
        mapController.setMarker(LatLng(45.2868868,18.802867))
        mapController.setMarker(LatLng(45.2886441,18.7968352))
        mapController.setMarker(LatLng(45.2880223,18.7964985))
        mapController.setMarker(LatLng(45.2925251,18.8016403))
        mapController.setMarker(LatLng(45.2877418,18.8014476))
        mapController.setMarker(LatLng(45.2870627,18.7999918))
        mapController.setMarker(LatLng(45.2858003,18.8090755))
        mapController.setMarker(LatLng(45.2935533,18.7944487))
        mapController.setMarker(LatLng(45.2899092,18.770863))
        mapController.setMarker(LatLng(45.2963761,18.7952923))
        mapController.setMarker(LatLng(45.2968032,18.799254))
        mapController.setMarker(LatLng(45.2934906,18.7942417))
        mapController.setMarker(LatLng(45.2902667,18.8084559))
        mapController.setMarker(LatLng(45.2938957,18.8035562))
        mapController.setMarker(LatLng(45.2968145,18.7975046))
        mapController.setMarker(LatLng(45.296787,18.7974329))
        mapController.setMarker(LatLng(45.2778029,18.7753905))
        mapController.setMarker(LatLng(45.2959486,18.7970906))
        mapController.setMarker(LatLng(45.2964231,18.7977907))
        mapController.setMarker(LatLng(45.2982637,18.8018626))
        mapController.setMarker(LatLng(45.2984363,18.8019615))
        mapController.setMarker(LatLng(45.2943211,18.7963315))
        mapController.setMarker(LatLng(45.2945814,18.7961411))
        mapController.setMarker(LatLng(45.2947629,18.7960655))
        mapController.setMarker(LatLng(45.2765198,18.7916536))
        mapController.setMarker(LatLng(45.2964321,18.7960247))
        mapController.setMarker(LatLng(45.2967708,18.7974248))
        mapController.setMarker(LatLng(45.2974872,18.8038399))
        mapController.setMarker(LatLng(45.2974408,18.8040482))
        mapController.setMarker(LatLng(45.295952,18.7962379))
        mapController.setMarker(LatLng(45.2957422,18.7962586))
        mapController.setMarker(LatLng(45.2929513,18.7915819))
        mapController.setMarker(LatLng(45.2942642,18.7944609))
        mapController.setMarker(LatLng(45.2953745,18.8041146))
        mapController.setMarker(LatLng(45.2953026,18.8043318))
        mapController.setMarker(LatLng(45.2957309,18.7921304))
        mapController.setMarker(LatLng(45.2967335,18.7951983))
        mapController.setMarker(LatLng(45.2977428,18.8021777))
        mapController.setMarker(LatLng(45.297953,18.8023019))
        mapController.setMarker(LatLng(45.2939719,18.8007532))
        mapController.setMarker(LatLng(45.2939108,18.8003827))
        mapController.setMarker(LatLng(45.2855017,18.8002875))
        mapController.setMarker(LatLng(45.2949798,18.7984358))
        mapController.setMarker(LatLng(45.2951426,18.7983751))
        mapController.setMarker(LatLng(45.2892563,18.8124418))
        mapController.setMarker(LatLng(45.2963833,18.7990291))
        mapController.setMarker(LatLng(45.2945402,18.797963))
        mapController.setMarker(LatLng(45.2935115,18.7938561))
        mapController.setMarker(LatLng(45.294486,18.7977503))
        mapController.setMarker(LatLng(45.2944322,18.7975155))
        mapController.setMarker(LatLng(45.2969013,18.7958045))

    }
    private fun setMarkersForIlok(){
        mapController.setMarker(LatLng(45.2244276,19.3611087))
        mapController.setMarker(LatLng(45.2244302,19.360982))
        mapController.setMarker(LatLng(45.22269,19.3754718))
    }
    private fun setMarkersForStaraGradiska(){
        mapController.setMarker(LatLng(45.1508759,17.2441388))//5
        mapController.setMarker(LatLng(45.150869, 17.244788))//6
        mapController.setMarker(LatLng(45.1508869,17.2439698))//7
        mapController.setMarker(LatLng(45.150890, 17.244372))//8
        mapController.setMarker(LatLng(45.149961, 17.244115))//13
        mapController.setMarker(LatLng(45.1508697,17.2423349))
        mapController.setMarker(LatLng(45.1508466,17.2421267))
        mapController.setMarker(LatLng(45.1508183,17.2419123))
        mapController.setMarker(LatLng(45.1507281,17.2399948))
        mapController.setMarker(LatLng(45.1508899,17.2438278))
        mapController.setMarker(LatLng(45.1506249,17.2402147))
        mapController.setMarker(LatLng(45.1507853,17.2412641))
        mapController.setMarker(LatLng(45.1506424,17.2405748))
        mapController.setMarker(LatLng(45.1507631,17.2406861))
        mapController.setMarker(LatLng(45.1507749,17.2411168))
        mapController.setMarker(LatLng(45.150799,17.2414581))
        mapController.setMarker(LatLng(45.1508069,17.241634))
        mapController.setMarker(LatLng(45.1499395,17.2407964))
        mapController.setMarker(LatLng(45.1492719,17.2408236))
        mapController.setMarker(LatLng(45.1499385,17.2409554))
        mapController.setMarker(LatLng(45.1492843,17.2412076))
        mapController.setMarker(LatLng(45.1499374,17.2411144))
        mapController.setMarker(LatLng(45.1499454,17.2412767))
        mapController.setMarker(LatLng(45.1499578,17.2414454))

    }
    private fun setMarkersForSlavonskiBrod(){
        mapController.setMarker(LatLng(45.158743,18.0209415))
        mapController.setMarker(LatLng(45.1591551,18.0136301))
    }
    private fun setMarkersForVukovar(){
        mapController.setMarker(LatLng(45.3560129,18.9978984))
        mapController.setMarker(LatLng(45.353632,18.9961691))
        mapController.setMarker(LatLng(45.3507383,19.0026448))

    }
    private fun setMarkersForOkucani(){
        mapController.setMarker(LatLng(45.2714524,17.2041063))
        mapController.setMarker(LatLng(45.2663409,17.2058762))
        mapController.setMarker(LatLng(45.2564855,17.200145))
    }
    private fun setMarkersForZaton(){
        mapController.setMarker(LatLng(44.227008, 15.171243))
    }
}