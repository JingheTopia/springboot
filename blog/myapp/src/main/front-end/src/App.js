import React from 'react';
import logo from './logo.svg';
import './App.css';
import axios from "axios";
import { useState, useEffect} from 'react';
import  {useCallback} from 'react'
import {useDropzone} from 'react-dropzone'



const UserProfiles = () => {

  const[userProfiles, setUserProfiles] = useState([]);

  const fetchUserProfiles = () => {
    axios.get("http://localhost:8080/api/v1/user-profile").then(res => {
      console.log(res);
      setUserProfiles(res.data);
    });
  };

  useEffect(() => {
    fetchUserProfiles()}, 
    []
  );
  return userProfiles.map((userProfile, index) => {
    return (
      <div key = {index}>
      <br />
      <br />
      <br />
      {userProfile.id ? (<img
        src = {`http://localhost:8080/api/v1/user-profile/${userProfile.id}/image/download`}
      />): null
      }
      <br />
      <h1>{userProfile.name}</h1>
      <p>{userProfile.id}</p>
      <MyDropzone  id = {userProfile.id}/>
      <br />
    </div>);
  });
};


function MyDropzone({id}) { 
  const onDrop = useCallback(acceptedFiles => {
    const file = acceptedFiles[0];
    console.log(file);

    const formData = new FormData();
    formData.append("file", file);

    axios.post(`http://localhost:8080/api/v1/user-profile/${id}/image/upload`,
      formData, {
        headers : {
          "Content-type" : "multipart/form-data"
        }
      }
    ).then(() => {
      console.log("file uploaded succefully")
    }).catch(err => {
      console.log(err);
    });
    // Do something with the files
  }, [])
  const {getRootProps, getInputProps, isDragActive} = useDropzone({onDrop})

  return (
    <div {...getRootProps()}>
      <input {...getInputProps()} />
      {
        isDragActive ?
          <p>Drop the images here ...</p> :
          <p>Drag 'n' drop images here, or click to select</p>
      }
    </div>
  )
}

function App() {

  return (
    <div className="App">
      <UserProfiles/>
    </div>
  );
}

export default App;
