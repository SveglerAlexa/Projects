// preferinte_model.dart
class PreferinteParcare {
  final bool inauntru; // true = inauntru, false = afara
  final int etaj; // 0 = nu se aplica, 1 sau 2
  final String intrarePreferata; // "A" sau "B"
  final bool pentruPersoaneCuDizabilitati;

  PreferinteParcare({
    required this.inauntru,
    required this.etaj,
    required this.intrarePreferata,
    required this.pentruPersoaneCuDizabilitati,
  });

  factory PreferinteParcare.fromJson(Map<String, dynamic> json) {
    return PreferinteParcare(
      inauntru: json['inauntru'] ?? false,
      etaj: json['etaj'] ?? 0,
      intrarePreferata: json['intrarePreferata'] ?? 'A',
      pentruPersoaneCuDizabilitati: json['pentruPersoaneCuDizabilitati'] ?? false,
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'inauntru': inauntru,
      'etaj': etaj,
      'intrarePreferata': intrarePreferata,
      'pentruPersoaneCuDizabilitati': pentruPersoaneCuDizabilitati,
    };
  }
}
