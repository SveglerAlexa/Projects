import 'dart:async';
import 'package:flutter/material.dart';
import '../models/parking_spot_from_api.dart';
import '../services/parking_api_service.dart';
import 'etaj1_map_screen.dart';

class ParkingMapInsideScreen extends StatefulWidget {
  final String floor; // "Parter"

  const ParkingMapInsideScreen({super.key, required this.floor});

  @override
  State<ParkingMapInsideScreen> createState() => _ParkingMapInsideScreenState();
}

class _ParkingMapInsideScreenState extends State<ParkingMapInsideScreen> {
  late Future<List<ParkingSpotFromApi>> _futureSpots;
  Timer? _timer; // 🔹 Timer pentru refresh automat

  static const double mapWidth = 1200;
  static const double mapHeight = 800;

  @override
  void initState() {
    super.initState();
    _loadSpots();

    // 🔹 Refresh automat la fiecare 5 secunde
    _timer = Timer.periodic(const Duration(seconds: 5), (timer) {
      _loadSpots();
    });
  }

  void _loadSpots() {
    _futureSpots = ParkingApiService.getParkingSpots();
    setState(() {}); // re-randeaza widget-ul
  }

  Widget _buildParkingSpot(ParkingSpotFromApi spot) {
    Color fillColor;
    Widget innerIcon;

    switch (spot.status.toLowerCase()) {
      case 'ocupat':
        fillColor = Colors.red;
        innerIcon = Stack(
          alignment: Alignment.center,
          children: [
            Transform.rotate(
              angle: 45 * 3.14159 / 180,
              child: Container(width: 20, height: 2, color: Colors.white),
            ),
            Transform.rotate(
              angle: -45 * 3.14159 / 180,
              child: Container(width: 20, height: 2, color: Colors.white),
            ),
          ],
        );
        break;
      case 'rezervat':
        fillColor = Colors.purple;
        innerIcon = const SizedBox.shrink();
        break;
      case 'liber':
      default:
        fillColor = Colors.green;
        innerIcon = const Icon(
          Icons.check,
          color: Colors.white,
          size: 20,
        );
        break;
    }

    return Positioned(
      left: spot.x,
      top: spot.y,
      child: GestureDetector(
        onTap: () {
          ScaffoldMessenger.of(context).showSnackBar(
            SnackBar(
              content: Text(
                'Loc selectat: ${spot.numarLoc}\nStatus: ${spot.status}',
              ),
            ),
          );
        },
        child: Container(
          width: 40,
          height: 40,
          decoration: BoxDecoration(
            color: fillColor,
            shape: BoxShape.circle,
            border: Border.all(color: Colors.black, width: 2),
          ),
          child: Center(child: innerIcon),
        ),
      ),
    );
  }

  @override
  void dispose() {
    _timer?.cancel(); // 🔹 Oprește timer-ul când widget-ul dispare
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Parcare Interioară - ${widget.floor}'),
        backgroundColor: Colors.white,
        foregroundColor: Colors.black,
        elevation: 0,
        actions: [
          if (widget.floor == 'Parter')
            TextButton.icon(
              onPressed: () {
                Navigator.push(
                  context,
                  MaterialPageRoute(
                    builder: (context) =>
                    const ParkingMapEtaj1Screen(floor: 'Etaj 1'),
                  ),
                );
              },
              icon: const Icon(Icons.arrow_upward, color: Colors.black),
              label: const Text(
                'Etaj 1',
                style: TextStyle(
                  color: Colors.black,
                  fontWeight: FontWeight.bold,
                ),
              ),
            ),
        ],
      ),
      body: FutureBuilder<List<ParkingSpotFromApi>>(
        future: _futureSpots,
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return const Center(child: CircularProgressIndicator());
          } else if (snapshot.hasError) {
            return Center(
              child: Text('Eroare: ${snapshot.error}'),
            );
          } else if (!snapshot.hasData || snapshot.data!.isEmpty) {
            return const Center(
              child: Text('Nu s-au găsit locuri de parcare'),
            );
          }

          // 🔹 Filtrare DOAR interior – Parter (etaj = 1)
          final spots = snapshot.data!
              .where((spot) {
            final rawEtaj = spot.etaj?.trim() ?? '';
            final etajInt = int.tryParse(rawEtaj);
            return etajInt == 1;
          })
              .toList();

          if (spots.isEmpty) {
            return const Center(
              child: Text(
                'Nu există locuri de parcare pentru Parter',
                style: TextStyle(fontSize: 18),
              ),
            );
          }

          return InteractiveViewer(
            minScale: 0.5,
            maxScale: 3.0,
            boundaryMargin: const EdgeInsets.all(300),
            child: SizedBox(
              width: mapWidth,
              height: mapHeight,
              child: Stack(
                children: [
                  Image.asset(
                    'assets/parcare_inauntru1.png',
                    width: mapWidth,
                    height: mapHeight,
                    fit: BoxFit.fill,
                  ),
                  ...spots.map(_buildParkingSpot),
                ],
              ),
            ),
          );
        },
      ),
    );
  }
}
