import 'package:flutter/material.dart';
import '../models/parking_spot_from_api.dart';
import '../models/sofer_model.dart';
import '../services/rezervare_api_service.dart';

class LocatieParcareScreen extends StatefulWidget {
  final Sofer sofer;

  const LocatieParcareScreen({super.key, required this.sofer});

  @override
  State<LocatieParcareScreen> createState() => _LocatieParcareScreenState();
}

class _LocatieParcareScreenState extends State<LocatieParcareScreen> {
  ParkingSpotFromApi? loc;
  bool loading = true;

  @override
  void initState() {
    super.initState();
    _load();
  }

  Future<void> _load() async {
    try {
      final result =
      await RezervareApiService.getParcareActiva(widget.sofer.id);
      setState(() {
        loc = result;
        loading = false;
      });
    } catch (e) {
      setState(() => loading = false);
      ScaffoldMessenger.of(context)
          .showSnackBar(SnackBar(content: Text('Eroare: $e')));
    }
  }

  String _titluEtaj(String etaj) {
    if (etaj == '0') return 'Parcare exterioară';
    if (etaj == '1') return 'Parcare interioară – Parter';
    return 'Parcare interioară – Etaj 1';
  }

  String _imagineEtaj(String etaj) {
    if (etaj == '0') return 'assets/parcare.png';
    if (etaj == '1') return 'assets/parcare_inauntru1.png';
    return 'assets/parcare_inauntru.png';
  }

  @override
  Widget build(BuildContext context) {
    if (loading) {
      return const Scaffold(
        body: Center(child: CircularProgressIndicator()),
      );
    }

    /// ===============================
    /// CAZ: NU ESTE PARCATĂ MAȘINA
    /// ===============================
    if (loc == null) {
      return Scaffold(
        extendBodyBehindAppBar: true,
        appBar: AppBar(
          backgroundColor: Colors.transparent,
          elevation: 0,
          title: const Text(
            'Locația mașinii',
            style: TextStyle(color: Colors.black),
          ),
          iconTheme: const IconThemeData(color: Colors.black),
        ),
        body: Stack(
          fit: StackFit.expand,
          children: [
            Image.asset(
              'assets/funadal_principal.jpg',
              fit: BoxFit.cover,
            ),
            Container(color: Colors.black.withOpacity(0.4)),
            SafeArea(
              child: Center(
                child: Container(
                  padding: const EdgeInsets.all(24),
                  margin: const EdgeInsets.symmetric(horizontal: 24),
                  decoration: BoxDecoration(
                    color: Colors.white.withOpacity(0.9),
                    borderRadius: BorderRadius.circular(12),
                  ),
                  child: Column(
                    mainAxisSize: MainAxisSize.min,
                    children: const [
                      Icon(Icons.block, size: 60, color: Colors.red),
                      SizedBox(height: 16),
                      Text(
                        'Nu aveți o mașină parcată',
                        style: TextStyle(
                          fontSize: 20,
                          fontWeight: FontWeight.bold,
                        ),
                        textAlign: TextAlign.center,
                      ),
                      SizedBox(height: 8),
                      Text(
                        'Parcați o mașină pentru a vedea locația acesteia.',
                        textAlign: TextAlign.center,
                      ),
                    ],
                  ),
                ),
              ),
            ),
          ],
        ),
      );
    }

    /// ===============================
    /// CAZ: MAȘINA ESTE PARCATĂ
    /// ===============================
    return Scaffold(
      appBar: AppBar(
        title: Text(_titluEtaj(loc!.etaj)),
      ),
      body: InteractiveViewer(
        minScale: 0.5,
        maxScale: 3,
        boundaryMargin: const EdgeInsets.all(300),
        child: SizedBox(
          width: 1200,
          height: 800,
          child: Stack(
            children: [
              Image.asset(
                _imagineEtaj(loc!.etaj),
                width: 1200,
                height: 800,
                fit: BoxFit.fill,
              ),

              /// marker parcare
              Positioned(
                left: loc!.x,
                top: loc!.y,
                child: Container(
                  width: 40,
                  height: 40,
                  decoration: BoxDecoration(
                    color: Colors.red,
                    shape: BoxShape.circle,
                    border: Border.all(color: Colors.black, width: 2),
                  ),
                  child: const Icon(
                    Icons.local_parking,
                    color: Colors.white,
                  ),
                ),
              ),

              /// detalii
              Positioned(
                top: 16,
                left: 16,
                child: Container(
                  padding: const EdgeInsets.all(10),
                  decoration: BoxDecoration(
                    color: Colors.black.withOpacity(0.6),
                    borderRadius: BorderRadius.circular(8),
                  ),
                  child: Text(
                    'Loc: ${loc!.numarLoc}',
                    style: const TextStyle(color: Colors.white),
                  ),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
