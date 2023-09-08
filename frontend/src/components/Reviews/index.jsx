import { PropTypes } from 'prop-types';

import ReviewCard from './ReviewCard';

const propTypes = {
    reviews: PropTypes.array.isRequired,
    error: PropTypes.string.isRequired
};

const Reviews = ({ reviews, error }) => {

    const reviewsDisplay = (reviews) => {
        return reviews.map(review => (
            <ReviewCard title={review.title} authorId={review.authorId} score={review.score} body={review.body} />
        ));
    }

    if(!reviews) {
        return <p>{error}</p>;
    } else if (reviews.length == 0) {
        return <p>No reviews exist for this restaurant yet. Why don't you write the first one?</p>
    } else {
        return (
            <div>{reviewsDisplay(reviews)}</div>
        );
    }
}

Reviews.propTypes = propTypes;

export default Reviews;
