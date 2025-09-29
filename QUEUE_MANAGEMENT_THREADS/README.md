Proiectul implementează o aplicație de management al cozilor, care simulează distribuirea clienților în mai multe cozi astfel încât timpul de așteptare să fie minimizat. Fiecare client este caracterizat printr-un ID, un timp de sosire și un timp de servire, iar fiecare coadă este procesată de un fir de execuție separat.



La fiecare pas de simulare, clienții disponibili sunt repartizați în coada cu timpul de așteptare cel mai mic. Aplicația generează un jurnal de evenimente care arată evoluția cozilor și calculează indicatori precum timpul mediu de așteptare, timpul mediu de servire și ora de vârf.



Utilizatorul introduce parametrii inițiali (număr de clienți, număr de cozi, interval de simulare, interval de sosire și de servire), iar aplicația rulează simularea folosind multithreading și structuri sincronizate. Rezultatele sunt afișate atât într-o interfață grafică (GUI), cât și într-un fișier text.

