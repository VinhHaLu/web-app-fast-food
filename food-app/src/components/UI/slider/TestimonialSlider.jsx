import React from "react";
import Slider from "react-slick";

import ava01 from "../../../assets/images/ava-1.jpg";
import ava02 from "../../../assets/images/ava-2.jpg";
import ava03 from "../../../assets/images/ava-3.jpg";

import "../../../styles/slider.css";

const TestimonialSlider = () => {
  const settings = {
    dots: true,
    autoplay: true,
    infinite: true,
    speed: 1000,
    autoplaySpeed: 3000,
    swipeToSlide: true,
    slidesToShow: 1,
    slidesToScroll: 1,
  };
  return (
    <Slider {...settings}>
      <div>
        <p className="review__text">
          "Thật tuyệt vời khi tôi được nếm thử những món ăn của các bạn.Nó thật sự rất tuyệt,
          tôi sẽ ủng hộ các bạn."
        </p>
        <div className=" slider__content d-flex align-items-center gap-3 ">
          <img src={ava01} alt="avatar" className=" rounded" />
          <h6>Ngọc Quý</h6>
        </div>
      </div>
      <div>
        <p className="review__text">
          "Thật tuyệt vời khi tôi được nếm thử những món ăn của các bạn.Nó thật sự rất tuyệt,
          tôi sẽ ủng hộ các bạn."
        </p>
        <div className="slider__content d-flex align-items-center gap-3 ">
          <img src={ava02} alt="avatar" className=" rounded" />
          <h6>Ngọc Nguyên</h6>
        </div>
      </div>
      <div>
        <p className="review__text">
          "Thật tuyệt vời khi tôi được nếm thử những món ăn của các bạn.Nó thật sự rất tuyệt,
          tôi sẽ ủng hộ các bạn."
        </p>
        <div className="slider__content d-flex align-items-center gap-3 ">
          <img src={ava03} alt="avatar" className=" rounded" />
          <h6>Phạm Hiển</h6>
        </div>
      </div>
    </Slider>
  );
};

export default TestimonialSlider;
