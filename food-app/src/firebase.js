import { initializeApp } from "firebase/app";
import { getDatabase } from "firebase/database";
import { getStorage } from "firebase/storage";

import {getAuth,GoogleAuthProvider} from "firebase/auth";
const firebaseConfig = {
  apiKey: "AIzaSyAYAokrLE373XNI9e3uvytQ_OtDvJ5vuHY",
  authDomain: "res-food-app.firebaseapp.com",
  databaseURL: "https://res-food-app-default-rtdb.firebaseio.com",
  projectId: "res-food-app",
  storageBucket: "res-food-app.appspot.com",
  messagingSenderId: "1015501084533",
  appId: "1:1015501084533:web:c2d44a13c30919236f653c",
  measurementId: "G-JP31NX8150"
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);
const storage = getStorage(app);
const db = getDatabase(app);

const auth = getAuth(app);
const provider = new GoogleAuthProvider();
export { db, storage, auth ,provider};

