package org.example.lr4_tech_roz_sys;

public class TrainClass {
        private String ID;
        private String NameM;
        private String TypeTrain;

        public TrainClass(String id, String nameM, String typeTrain) {
            this.ID = id;
            this.NameM = nameM;
            this.TypeTrain = typeTrain;
        }
        public String getId() {return ID;}
        public void setId(String id) {
            this.ID = id;
        }
        public String getNameM() {
            return NameM;
        }
        public void setNameM(String nameM) {this.NameM = nameM;}
        public String getTypeTrain() {return TypeTrain;}
        public void setTypeTrain(String typeTrain) {this.TypeTrain = typeTrain;}
    }

